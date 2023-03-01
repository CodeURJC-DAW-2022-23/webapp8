/**
 * Notification types and its texts
 */

notifs = {
    "LIKE": "liked your Tweet",
    "RETWEET": "Retweeted your tweet",
    "FOLLOW": "followed you",
    "MENTION": "mentioned you in a Tweet"
}

function loadNotificationText(notificationId, notificationType){
    let notificationH2 = document.getElementById(notificationId + "userH2");
    let textToLoad = notifs[notificationType];
    notificationH2.innerHTML += textToLoad;
}