// This file is a Service worker used for Firebase Cloud Messaging

console.log('in sw, ', self)
importScripts('https://www.gstatic.com/firebasejs/4.8.1/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/4.8.1/firebase-messaging.js');

//import 'firebase/firebase-messaging.js'

//import config from '../src/unicommunity-config.json'
const config = {
    "apiKey": "AIzaSyDUKMc1kpuv192IesEX12OPZS2XFT9N3-8",
    "authDomain": "unicommunity-f7bc8.firebaseapp.com",
    "databaseURL": "https://unicommunity-f7bc8.firebaseio.com",
    "projectId": "unicommunity-f7bc8",
    "storageBucket": "unicommunity-f7bc8.appspot.com",
    "messagingSenderId": "256988232026",
    "appId": "1:256988232026:web:1b5543475b3d874c"
}

// The core Firebase JS SDK is always required
//import * as firebase from 'firebase'

firebase.initializeApp(config)

const messaging = firebase.messaging()
/*
messaging.onMessage(payload => {
    console.log(1)
    console.log({payload})
}, e => console.log({e}), c => console.log({c}))*/