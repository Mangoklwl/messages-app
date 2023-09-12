const mongoose = require('mongoose');
const sha256 = require('sha256');
const express = require('express');
const bodyparse = require('body-parser');
const jwt = require('jsonwebtoken')
const secretWord = "sdffsdfsdfwer234523452345234r3r23452";
const fs=require('fs');
const { getSystemErrorMap } = require('util');

mongoose.Promise = global.Promise;
mongoose.connect('mongodb://127.0.0.1:27017/message-services') // conexcion con la base de datos
mongoose.set('strictQuery', false)
let app = express();
app.use(bodyparse.json());

//----- estructura de users---
let usersSchema = new mongoose.Schema({

    name: {
        type: String,
        requiered: true,
        trim: true,
        match: /^[a-z]+$/i, // coge letras de a a z , con la i coge mayus
        min: 1

    },
    password: {
        type: String,
        requiered: true,
        min: 4

    },
    image: {
        type: String,
        requiered: true
    }

});

let userModel = mongoose.model('users', usersSchema)
let messageSchema = new mongoose.Schema({
    from: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'users',
        requiered: true
    },
    to: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'users',
        requiered: true
    },
    message: {
        type: String,
        requiered: true,
        trim: true,
        min: 1
    },
    image: {
        type: String,
        requiered: false
    },
    sent: {
        type: String,
        requiered: true,
        trim: true,
        min: 10,
    }

});
let messageModel = mongoose.model('messages', messageSchema)
//---borra mensajes--
app.delete('/messagesDel/:id', (req, res) => {
    messageModel.findByIdAndRemove(req.params.id).then(result => {
        let data = {
            ok: true
        };
        res.send(data);
    }).catch(error => {
        res.status(400);
        let data = {
            ok: false,
            errorMessage: "Error deleting message:",
            result: _id
        };
        res.send(data);
    });
});
//-------inserta un mensaje----
app.post('/insetarmen/:to', (req, res) => {
    let token = req.headers['authorization'];
    let result = validateToken(token);

    let decoded = jwt.decode(token,{complete: true, json:true})
    let idUsu = decoded.payload.id

    
    
    //let user=userModel.findOne({name: result.name});
    if (result) {
        let message = new messageModel({ // insertamos un estudiante
            from: idUsu,
            to: req.params.to,
            message: req.body.message,
            image: req.body.image,
            sent: req.body.send,
        });

        message.save()
            .then(result => {
                res.send({
                    ok: true,
                    message: result
                });
            })
            .catch(error => {
                res.status(400);
                res.send({
                    code: 401,
                    message: error
                });
            });

    } else {
        res.status(400);
        res.send({
            code: 401,
            message: "token no enviado"
        });
    }


})


app.get('/messages', (req, res) => {

    let token = req.headers['authorization'];
    let result = validateToken(token); //valida el token

    
    let decoded = jwt.decode(token,{complete: true, json:true})
    let idUsu = decoded.payload.id

    if (result) {
        messageModel.find({from:idUsu})

            .then(result => {

                res.send({
                    ok: true,
                    messages: result
                });
            })

            .catch(error => {
                res.status(400);
                res.send({
                    code: 400,
                    message: error
                });
            });

    } else {
        res.status(400);
        res.send({
            code: 401,
            message: "token no enviado"
        });
    }

})
//----genera un token----
let generateToken = (name, id) => {
    let token = jwt.sign({
        name: name,
        id: id
    },
        secretWord, //string para firmar
        {
            expiresIn: "123 minutes"
        });
    return token;
}
//------------------------------------------------

//-----------autenticacion del usuario------------
let validateToken = token => {
    try {
        let result = jwt.verify(token, secretWord); // mira si esta firmado con nuestra secretword
        return result;
    } catch (e) { }
}
//-------funcion que busca usuarios
app.get('/buscarusuarios', (req, res) => {

    let token = req.headers['authorization'];
    let result = validateToken(token); //valida el token
    if (result) {
        userModel.find({})

            .then(result => {

                res.send({
                    ok: true,
                    users: result
                });
            })

            .catch(error => {
                res.status(400);
                res.send({
                    code: 400,
                    message: error
                });
            });

    } else {
        res.status(400);
        res.send({
            code: 402,
            message: "el token es incorrecto"
        });
    }

})
//-----funfion de registro

app.post('/registro', (req, res) => {
    let name = req.body.name;
    userModel.find({
        name: name
    })
        .then(result => {
            if (result.length > 0) {
                res.status(400);
                res.send({
                    code: 400,
                    message: "el usuario ya existe"
                });
            } else {
                let Users = new userModel({
                    name: req.body.name,
                    password: req.body.password,
                    image: req.body.image,
                    

                });

                Users.save()
                    .then(result => {
                        console.log(result);

                        res.send({
                            code: 200,
                            id: result._id,
                            image: result.image,
                            password: result.password
                        })
                    })
                    .catch(error => {
                        res.status(400);
                        res.send({
                            code: 400,
                            message: error
                        })
                    })
            }


        })
        .catch(error => {
            res.status(400);
            res.send({
                code: 400,
                message: error
            });
        });


})


//-----------------

//----funcion de login----
app.post('/login', (req, res) => {

    let name = req.body.name;
    let password = req.body.password;

    userModel.find({
        name: name
    })
        .then(result => {
            
            if (result[0].password === password) {
                res.send({
                    ok: true,
                    token: generateToken(name, result[0]._id),
                    name: name,
                    image: result[0].image
                })
            } else {
                res.status(400);
                res.send({
                    code: 401,
                    message: "user invalid"
                });
            }
        })
        .catch(error => {
            res.status(400);
            res.send({
                code: 400,
                message: "user invalid"
            })
        })


})

// te saca un mesaje si se conecta
var conectar = mongoose.connection;
conectar.on('Error', (err) => {
    
    console.log("Error al conectar" + err)
});
conectar.on('open', () => {
    console.log("Conectado a la base de datos")
})
//-----------------------

//----actualiza image----
app.put('/users', (req, res) => {

    let token = req.headers['authorization'];
    let result = validateToken(token); //valida el token

    let decoded = jwt.decode(token,{complete: true, json:true})
    let idUsu = decoded.payload.id
    
    if(result) {
        userModel.findByIdAndUpdate(idUsu, {
            $set: {
    
                image: req.body.image,
            }
        }, {
            new: true
        })
        
        .then(result => {
            let data = {
                ok: true
            };
            res.send(data)
        })
        
        .catch(error => {
            res.status(400);
            let data = {
                ok: false,
                errorMessage: "error updating user:",
                result: id
            };
            res.send(data);
    
        });
    } else {
        res.status(400);
        res.send({
            code: 402,
            message: "el token es incorrecto"
        });
    }
    
});




app.listen(8080);