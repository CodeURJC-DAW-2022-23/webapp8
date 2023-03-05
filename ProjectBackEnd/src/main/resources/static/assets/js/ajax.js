const DECODER = new TextDecoder('iso-8859-1');
const NUMBER_ELEMENTS_PER_LOAD = 10;
let ACTUAL_HASHTAG;
let ACTUAL_PROFILE;

let counterPetitions = 0;

/**
 * Realize an HTTP petition to request more trends with AJAX
 * @returns {Promise<void>}
 */
async function loadMoreTrends() {
    const from = (counterPetitions + 1) * NUMBER_ELEMENTS_PER_LOAD

    const response = await fetch(`/explore/trends?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);
    
    if (response.redirected) {
        hideButtons();
        return;
    }
    
    addNewElements(response, "trend");
}

/**
 * Realize an HTTP petition to request more notifications with AJAX
 * @returns {Promise<void>}
 */
async function loadMoreTweetsForBookmarks() {
    const from = (counterPetitions + 1) * NUMBER_ELEMENTS_PER_LOAD

    const response = await fetch(`/bookmarks/tweets?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);

    if (response.redirected) {
        hideButtons();
        return;
    }
    
    addNewElements(response, "tweet");
}

/**
 * Realize an HTTP petition to request more tweets with AJAX at home page
 * @returns {Promise<void>}
 */
async function loadMoreTweetsForHome() {
    const from = (counterPetitions + 1) * NUMBER_ELEMENTS_PER_LOAD

    const response = await fetch(`/home/tweets?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);
    
    if (response.redirected) {
        hideButtons();
        return;
    }
    
    addNewElements(response, "tweet");
}

/**
 * Realize an HTTP petition to request more tweets with AJAX at home page
 * @returns {Promise<void>}
 */
async function loadMoreTweetsForProfile(userId) {
    const from = (counterPetitions + 1) * NUMBER_ELEMENTS_PER_LOAD
    
    const response = await fetch(`/profile/tweets/${userId}?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);

    if (response.redirected) {
        hideButtons();
        return;
    }

    addNewElements(response, "tweet");
}

async function loadMoreNotifications() {
    const from = (counterPetitions + 1) * NUMBER_ELEMENTS_PER_LOAD

    const response = await fetch(`/notifications/notification?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);

    if (response.redirected) {
        hideButtons();
        return;
    }

    addNewElements(response, "notification");
}

async function loadMoreMentions() {
    const from = (counterPetitions + 1) * NUMBER_ELEMENTS_PER_LOAD

    const response = await fetch(`/mentions/mention?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);

    if (response.redirected) {
        hideButtons();
        return;
    }

    addNewElements(response, "notification");
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

async function loadMoreTweetsAssociated(){
    const from = (counterPetitions + 1) * NUMBER_ELEMENTS_PER_LOAD

    const response = await fetch(`/explore_more/${ACTUAL_HASHTAG}?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);

    if (response.redirected) {
        hideButtons();
        return;
    }

    addNewElements(response, "trend");
}

async function showTweetsAssociated(hashtag) {
    counterPetitions = 0;
    const response = await fetch(`/explore/${hashtag}`);
    const newTrends = DECODER.decode(await response.arrayBuffer());

    const container = document.getElementById("trend-container");
    container.innerHTML = newTrends;
    const load_more = document.getElementById("loadMoreButton");
    load_more.onclick = loadMoreTweetsAssociated;
    const load_more_mobile = document.getElementById("loadMoreButtonMobile");
    load_more_mobile.onclick = loadMoreTweetsAssociated;
    ACTUAL_HASHTAG = hashtag;
}

async function showFollowers(userId) {
    counterPetitions = 0;

    if (userId) {
        ACTUAL_PROFILE = userId;
    }

    const response = await fetch(`/followers/${ACTUAL_PROFILE}/${counterPetitions}/${NUMBER_ELEMENTS_PER_LOAD}`);
    const newFollowers = DECODER.decode(await response.arrayBuffer());

    const container = document.getElementById("follow-container");
    container.innerHTML = newFollowers;
    const load_more = document.getElementById("loadMoreButton");
    load_more.onclick = loadMoreFollowers;
    const load_more_mobile = document.getElementById("loadMoreButtonMobile");
    load_more_mobile.onclick = loadMoreFollowers;
}

async function loadMoreFollowers() {
    const from = (counterPetitions + 1) * NUMBER_ELEMENTS_PER_LOAD

    const response = await fetch(`/followers/${ACTUAL_PROFILE}/${from}/${NUMBER_ELEMENTS_PER_LOAD}`);

    if (response.redirected) {
        hideButtons();
        return;
    }

    addNewElements(response, "follow");
}

async function showFollowed() {
    counterPetitions = 0;

    const response = await fetch(`/followed/${ACTUAL_PROFILE}/${counterPetitions}/${NUMBER_ELEMENTS_PER_LOAD}`);
    const newFollowed = DECODER.decode(await response.arrayBuffer());

    const container = document.getElementById("follow-container");
    container.innerHTML = newFollowed;
    const load_more = document.getElementById("loadMoreButton");
    load_more.onclick = loadMoreFollowed;
    const load_more_mobile = document.getElementById("loadMoreButtonMobile");
    load_more_mobile.onclick = loadMoreFollowed;
}

async function loadMoreFollowed() {
    const from = (counterPetitions + 1) * NUMBER_ELEMENTS_PER_LOAD

    const response = await fetch(`/followed/${ACTUAL_PROFILE}/${from}/${NUMBER_ELEMENTS_PER_LOAD}`);

    if (response.redirected) {
        hideButtons();
        return;
    }

    addNewElements(response, "follow");
}

/**
 * Add the new elements obtained in the correspondient container
 * @param {HTTP} response
 * @param {String} container_name
 */
async function addNewElements(response, container_name) {
    const newElements = DECODER.decode(await response.arrayBuffer());

    const container = document.getElementById(container_name + "-container");
    container.innerHTML += newElements;

    counterPetitions++;
}

/**
 * Hide the load more buttons when is not possible to load more elements
 */
function hideButtons() {
    changeVisibility(document.getElementById("loadMoreButton"));
    changeVisibility(document.getElementById("loadMoreButtonMobile"));
};

/**
 * Change the current element visibility
 * @param {String} element 
 */
function changeVisibility(element) {
    element.classList.add('hidden');
};