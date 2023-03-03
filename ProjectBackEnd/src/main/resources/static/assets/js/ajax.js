const NUMBER_ELEMENTS_PER_LOAD = 10;

let counterPetitions = 0;

/**
 * Realize an HTTP petition to request more trends with AJAX
 * @returns {Promise<void>}
 */
async function loadMoreTrends() {
    const from = counterPetitions + NUMBER_ELEMENTS_PER_LOAD;

    const response = await fetch(`/explore/trends?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);
    const newTrends = await response.text();

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
    const newNotifications = await response.text();

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
    const newTweets = await response.text();

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
    const newTweets = await response.text();

    const container = document.getElementById("tweet-container");
    container.innerHTML += newTweets;

    counterPetitions++;
}