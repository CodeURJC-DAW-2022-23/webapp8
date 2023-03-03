/**
 * Give o remove a like to a tweet
 * @param {String} tweet_id 
 * @returns {Promise<void>}
 */
async function giveLike(tweet_id) {
    let id_container = "like-" + tweet_id;
    const like_container = document.getElementById(id_container);
    const like_svg = document.querySelector(`#like-svg-${tweet_id}`);
    let text = like_container.textContent;
    let value = parseInt(text);

    const response = await fetch(`/tweet/like/${tweet_id}`);
    const state = response.redirected;

    if (!state) {
        value++;       
    } else {
        value--;
    }

    like_container.classList.toggle('text-red-0');
    like_svg.classList.toggle('fill-red-0');
    like_container.textContent = value;
};

async function giveRetweet(tweet_id) {
    const retweet_container = document.querySelector('#retweet-' + tweet_id);
    const retweet_svg = document.querySelector('#retweet-svg-' + tweet_id);
    let text = retweet_container.textContent;
    let value = parseInt(text);

    const response = await fetch(`/tweet/retweet/${tweet_id}`);
    const state = response.redirected;

    if (!state) {
        value++;       
    } else {
        value--;
    }

    retweet_container.classList.toggle('text-green-0');
    retweet_svg.classList.toggle('fill-green-0');
    retweet_container.textContent = value;
};

async function giveBookmark(tweet_id) {

};