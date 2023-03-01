const NUMBER_ELEMENTS_PER_LOAD = 10;

let counterMoreTrends = 0;


/**
 * Realize an HTTP petition to request more trends with AJAX
 * @returns {Promise<void>}
 */
async function loadMoreTrends() {
    const from = counterMoreTrends + 1;

    const response = await fetch(`/explore/trends?from=${from}&size=${NUMBER_ELEMENTS_PER_LOAD}`);
    const newTrends = await response.text();

    const container = document.getElementById("trend-container");
    container.innerHTML += newTrends;

    counterMoreTrends++;
}