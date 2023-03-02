

function chargeNumComments(comments, id){
    console.log(comments);
    console.log(id);
    const commentsPlace = document.querySelector('#comments-' + id);
    const numComments = comments.length;
    commentsPlace.textContent = numComments;
}

function chargeNumRetweets(retweets, id) {
    console.log(retweets);
    console.log(id);
    const retweetsPlace = document.querySelector('#retweet-' + id);
    const numRetweets = retweets.length;
    retweetsPlace.textContent = numRetweets;
}

function chargeNumLikes(likes, id) {
    console.log(likes);
    console.log(id);
    const likesPlace = document.querySelector('#likes-' + id);
    const numLikes = likes.length;
    console.log(numLikes);
    likesPlace.textContent = numLikes;
}