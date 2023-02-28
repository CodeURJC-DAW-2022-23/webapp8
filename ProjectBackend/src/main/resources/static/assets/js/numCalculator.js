

function chargeNumComments(comments, id){
    const commentsPlace = document.querySelector('#comments-' + id);
    const numComments = comments.length;
    commentsPlace.textContent = numComments;
}

function chargeNumRetweets(retweets, id) {
    const retweetsPlace = document.querySelector('#retweet-' + id);
    const numRetweets = retweets.length;
    retweetsPlace.textContent = numRetweets;
}

function chargeNumLikes(likes, id) {
    const likesPlace = document.querySelector('#likes-' + id);
    const numLikes = likes.length;
    likesPlace.textContent = numLikes;
}