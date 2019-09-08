/**
 * This file is a Service worker that allows our app to receive the onMessage event, since 
 * "the messaging service requires a firebase-messaging-sw.js file". This statement can be found in
 * {@link https://firebase.google.com/docs/cloud-messaging/js/client#retrieve-the-current-registration-token|FCM docs}
 * {@link https://firebase.google.com/docs/cloud-messaging/js/receive|FCM receive message docs}
 */
importScripts('https://www.gstatic.com/firebasejs/4.8.1/firebase-app.js')
importScripts('https://www.gstatic.com/firebasejs/4.8.1/firebase-messaging.js')

const config = {
    "apiKey": "AIzaSyDUKMc1kpuv192IesEX12OPZS2XFT9N3-8",
    "authDomain": "unicommunity-f7bc8.firebaseapp.com",
    "databaseURL": "https://unicommunity-f7bc8.firebaseio.com",
    "projectId": "unicommunity-f7bc8",
    "storageBucket": "unicommunity-f7bc8.appspot.com",
    "messagingSenderId": "256988232026",
    "appId": "1:256988232026:web:1b5543475b3d874c"
}
firebase.initializeApp(config)

const messaging = firebase.messaging()

/**
This is necessary in order for the app to receive onMessage events.
{@link https://firebase.google.com/docs/reference/js/firebase.messaging.Messaging.html?authuser=1#on-message}
*/
messaging.setBackgroundMessageHandler(payload => {
  console.log('[firebase-messaging-sw.js] Received background message ', payload)
  const notificationTitle = 'Background Message Title'
  const notificationOptions = {
    body: 'Background Message body.'
  }

  return self.registration.showNotification(notificationTitle, notificationOptions)
})