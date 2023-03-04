const DECODER = new TextDecoder('iso-8859-1');
const NUMBER_ELEMENTS_PER_LOAD = 10;

let counterPetitions = 0;

/**
 * Realize an HTTP petition to request more trends with AJAX
 * @returns {Promise<void>}
 */
async function loadMoreTrends() {
    const from = counterPetitions + NUMBER_ELEMENTS_PER_LOAD;

    const response = await fetch(`/explore/trends?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);
    const newTrends = DECODER.decode(await response.arrayBuffer());

    const container = document.getElementById("trend-container");
    container.innerHTML += newTrends;

    counterPetitions++;
}

/**
 * Realize an HTTP petition to request more notifications with AJAX
 * @returns {Promise<void>}
 */
async function loadMoreTweetsForBookmarks() {
    const from = counterPetitions + 1;

    const response = await fetch(`/bookmarks/tweets?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);
    const newNotifications = DECODER.decode(await response.arrayBuffer());

    const container = document.getElementById("tweet-container");
    container.innerHTML += newNotifications;

    counterPetitions++;
}

/**
 * Realize an HTTP petition to request more tweets with AJAX at home page
 * @returns {Promise<void>}
 */
async function loadMoreTweetsForHome() {
    const from = counterPetitions + 1;

    const response = await fetch(`/home/tweets?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);
    const newTweets = DECODER.decode(await response.arrayBuffer());

    const container = document.getElementById("tweet-container");
    container.innerHTML += newTweets;

    counterPetitions++;
}

/**
 * Realize an HTTP petition to request more tweets with AJAX at home page
 * @returns {Promise<void>}
 */
async function loadMoreTweetsForProfile() {
    const from = counterPetitions + 1;

    const response = await fetch(`/profile/tweets?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);
    const newTweets = DECODER.decode(await response.arrayBuffer());

    const container = document.getElementById("tweet-container");
    container.innerHTML += newTweets;

    counterPetitions++;
}

async function loadMoreNotifications() {
    const from = counterPetitions + 1;

    const response = await fetch(`/notifications/notification?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);
    const newTweets = DECODER.decode(await response.arrayBuffer());

    const container = document.getElementById("notification-container");
    container.innerHTML += newTweets;

    counterPetitions++;
}

async function loadMoreMentions() {
    const from = counterPetitions + 1;

    const response = await fetch(`/mentions/mention?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);
    const newTweets = DECODER.decode(await response.arrayBuffer());

    const container = document.getElementById("notification-container");
    container.innerHTML += newTweets;

    counterPetitions++;
}

async function showMentions() {
    counterPetitions = 0;

    const response = await fetch(`/mentions?from=${counterPetitions}&size=${NUMBER_ELEMENTS_PER_LOAD}`);
    const newMentions = DECODER.decode(await response.arrayBuffer());

    const container = document.getElementById("notification-container");
    container.innerHTML = newMentions;
    const load_more = document.getElementById("loadMore");
    load_more.onclick = loadMoreMentions;
    const load_more_mobile = document.getElementById("loadMore-mobile");
    load_more_mobile.onclick = loadMoreMentions;
}

async function showNotifications() {
    counterPetitions = 0;

    const response = await fetch(`/all-notifications?from=${counterPetitions}&size=${NUMBER_ELEMENTS_PER_LOAD}`);
    const newNotifications = DECODER.decode(await response.arrayBuffer());

    const container = document.getElementById("notification-container");
    container.innerHTML = newNotifications;
    const load_more = document.getElementById("loadMore");
    load_more.onclick = loadMoreNotifications;
    const load_more_mobile = document.getElementById("loadMore-mobile");
    load_more_mobile.onclick = loadMoreNotifications;
}

async function loadTweetsAssociated(hashtag) {
    const response = await fetch(`/explore/${hashtag}`);
    const newTrends = DECODER.decode(await response.arrayBuffer());

    const container = document.getElementById("trend-container");
    container.innerHTML = newTrends;
}