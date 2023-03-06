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
    addSpinner()
    const from = (counterPetitions + 1) * NUMBER_ELEMENTS_PER_LOAD

    const response = await fetch(`/explore/trends?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);
    
    if (response.redirected) {
        hideButtons();
        removeSpinner();
        return;
    }
    addNewElements(response, "trend");
    removeSpinner()
}

function updateNumbers(){

}

/**
 * Realize an HTTP petition to request more notifications with AJAX
 * @returns {Promise<void>}
 */
async function loadMoreTweetsForBookmarks() {
    addSpinner()
    const from = (counterPetitions + 1) * NUMBER_ELEMENTS_PER_LOAD

    const response = await fetch(`/bookmarks/tweets?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);

    if (response.redirected) {
        hideButtons();
        removeSpinner()
        return;
    }
    
    addNewElements(response, "tweet");
    removeSpinner()
}

/**
 * Realize an HTTP petition to request more tweets with AJAX at home page
 * @returns {Promise<void>}
 */
async function loadMoreTweetsForHome() {
    addSpinner()
    const from = (counterPetitions + 1) * NUMBER_ELEMENTS_PER_LOAD

    const response = await fetch(`/home/tweets?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);
    
    if (response.redirected) {
        hideButtons();
        removeSpinner()
        return;
    }
    
    addNewElements(response, "tweet");
    removeSpinner()
}

/**
 * Realize an HTTP petition to request more tweets with AJAX at home page
 * @returns {Promise<void>}
 */
async function loadMoreTweetsForProfile(userId) {
    addSpinner()
    const from = (counterPetitions + 1) * NUMBER_ELEMENTS_PER_LOAD
    
    const response = await fetch(`/profile/tweets/${userId}?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);

    if (response.redirected) {
        hideButtons();
        removeSpinner()
        return;
    }

    addNewElements(response, "tweet");
    removeSpinner()
}

async function loadMoreNotifications() {
    addSpinner()
    const from = (counterPetitions + 1) * NUMBER_ELEMENTS_PER_LOAD

    const response = await fetch(`/notifications/notification?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);

    if (response.redirected) {
        hideButtons();
        removeSpinner()
        return;
    }

    addNewElements(response, "notification");
    removeSpinner()
}

async function loadMoreMentions() {
    addSpinner()
    const from = (counterPetitions + 1) * NUMBER_ELEMENTS_PER_LOAD

    const response = await fetch(`/mentions/mention?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);

    if (response.redirected) {
        removeSpinner()
        hideButtons();
        return;
    }

    addNewElements(response, "notification");
    removeSpinner()
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
    addSpinner()
    const from = (counterPetitions + 1) * NUMBER_ELEMENTS_PER_LOAD

    const response = await fetch(`/explore_more/${ACTUAL_HASHTAG}?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);

    if (response.redirected) {
        hideButtons();
        removeSpinner()
        return;
    }

    addNewElements(response, "trend");
    removeSpinner()
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
}

async function showFollowed() {
    counterPetitions = 0;

    const response = await fetch(`/followed/${ACTUAL_PROFILE}/${counterPetitions}/${NUMBER_ELEMENTS_PER_LOAD}`);
    const newFollowed = DECODER.decode(await response.arrayBuffer());

    const container = document.getElementById("follow-container");
    container.innerHTML = newFollowed;
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

    if(container_name==="trend"){
        for(i=((counterPetitions+1) * NUMBER_ELEMENTS_PER_LOAD);i<container.children.length;i++){
            let childNode = container.children.item(i).childNodes.item(1);
            let trendNumber = (Number(childNode.innerHTML.charAt(0))) + ((counterPetitions+1) * NUMBER_ELEMENTS_PER_LOAD);
            childNode.innerHTML = trendNumber + ' - Trending';
        }
    }
    counterPetitions++;
}

/**
 * Hide the load more buttons when is not possible to load more elements
 */
function hideButtons() {
    changeVisibility(document.getElementById("loadMore"));
    changeVisibility(document.getElementById("loadMore-mobile"));
};

/**
 * Change the current element visibility
 * @param {HTMLElement} element
 */
function changeVisibility(element) {
    element.classList.add('hidden');
    element.classList.remove('llg:block')
};

function addSpinner(){
    document.getElementById("spinner").innerHTML=`<div class="flex items-center justify-center">
                <div class="inline-block h-8 w-8 animate-spin rounded-full border-4 border-primary border-current border-r-transparent align-[-0.125em] motion-reduce:animate-[spin_1.5s_linear_infinite]" role="status">
                    <span class="!absolute !-m-px !h-px !w-px !overflow-hidden !whitespace-nowrap !border-0 !p-0 ![clip:rect(0,0,0,0)]">
                        Loading...
                    </span>
                </div>
            </div>`
}

function removeSpinner(){
    document.getElementById("spinner").innerHTML=``
}