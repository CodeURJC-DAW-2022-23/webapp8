/**
 * Notification types and its SVGs
 */

notifsSVG = {
    "LIKE": `
    <svg viewBox="0 0 24 24" aria-hidden="true" class="w-8 fill-red-0">
                            <g>
                                <path
                                    d="M20.884 13.19c-1.351 2.48-4.001 5.12-8.379 7.67l-.503.3-.504-.3c-4.379-2.55-7.029-5.19-8.382-7.67-1.36-2.5-1.41-4.86-.514-6.67.887-1.79 2.647-2.91 4.601-3.01 1.651-.09 3.368.56 4.798 2.01 1.429-1.45 3.146-2.1 4.796-2.01 1.954.1 3.714 1.22 4.601 3.01.896 1.81.846 4.17-.514 6.67z">
                                </path>
                            </g>
                        </svg>
    `,
    "RETWEET": `
    <svg viewBox="0 0 24 24" aria-hidden="true" class="w-8 fill-green-0">
                            <g>
                                <path 
                                    d="M4.5 3.88l4.432 4.14-1.364 1.46L5.5 7.55V16c0 1.1.896 2 2 2H13v2H7.5c-2.209 0-4-1.79-4-4V7.55L1.432 9.48.068 8.02 4.5 3.88zM16.5 6H11V4h5.5c2.209 0 4 1.79 4 4v8.45l2.068-1.93 1.364 1.46-4.432 4.14-4.432-4.14 1.364-1.46 2.068 1.93V8c0-1.1-.896-2-2-2z">
                                </path>
                            </g>
                        </svg>
    `,
    "FOLLOW": `
    <svg viewBox="0 0 24 24" aria-hidden="true" class="w-8 fill-primary">
                            <g>
                                <path
                                    d="M17.863 13.44c1.477 1.58 2.366 3.8 2.632 6.46l.11 1.1H3.395l.11-1.1c.266-2.66 1.155-4.88 2.632-6.46C7.627 11.85 9.648 11 12 11s4.373.85 5.863 2.44zM12 2C9.791 2 8 3.79 8 6s1.791 4 4 4 4-1.79 4-4-1.791-4-4-4z">
                                </path>
                            </g>
                        </svg>
    `,
    "MENTION": `
    <svg viewBox="0 0 24 24" aria-hidden="true" class="w-8 fill-violet-0">
                            <g>
                                <path
                                    d="M22.99 11.295l-6.986-2.13-.877-.326-.325-.88L12.67.975c-.092-.303-.372-.51-.688-.51-.316 0-.596.207-.688.51l-2.392 7.84-1.774.657-6.148 1.82c-.306.092-.515.372-.515.69 0 .32.21.6.515.69l7.956 2.358 2.356 7.956c.09.306.37.515.69.515.32 0 .6-.21.69-.514l1.822-6.15.656-1.773 7.84-2.392c.303-.09.51-.37.51-.687 0-.316-.207-.596-.51-.688z">
                                </path>
                            </g>
                        </svg>
    `
}

function loadSVG(notificationId, notificationType){
    let notificationFigure = document.getElementById(notificationId + "svg");
    let svgToLoad = notifsSVG[notificationType];
    notificationFigure.innerHTML = svgToLoad;
}


/**
 * User types and its name svgs
 */

users = {
    "PUBLIC": "",
    "PRIVATE": `
    <svg viewBox="0 0 24 24" class="fill-black-1 dark:fill-gray-3">
                                    <g>
                                        <path
                                            d="M17.5 7H17v-.25c0-2.76-2.24-5-5-5s-5 2.24-5 5V7h-.5C5.12 7 4 8.12 4 9.5v9C4 19.88 5.12 21 6.5 21h11c1.39 0 2.5-1.12 2.5-2.5v-9C20 8.12 18.89 7 17.5 7zM13 14.73V17h-2v-2.27c-.59-.34-1-.99-1-1.73 0-1.1.9-2 2-2 1.11 0 2 .9 2 2 0 .74-.4 1.39-1 1.73zM15 7H9v-.25c0-1.66 1.35-3 3-3 1.66 0 3 1.34 3 3V7z">
                                        </path>
                                    </g>
                                </svg>
    `,
    "VERIFIED": `
    <svg viewBox="0 0 24 24" class="w-5 fill-primary">
                                    <g>
                                        <path
                                            d="M22.25 12c0-1.43-.88-2.67-2.19-3.34.46-1.39.2-2.9-.81-3.91s-2.52-1.27-3.91-.81c-.66-1.31-1.91-2.19-3.34-2.19s-2.67.88-3.33 2.19c-1.4-.46-2.91-.2-3.92.81s-1.26 2.52-.8 3.91c-1.31.67-2.2 1.91-2.2 3.34s.89 2.67 2.2 3.34c-.46 1.39-.21 2.9.8 3.91s2.52 1.26 3.91.81c.67 1.31 1.91 2.19 3.34 2.19s2.68-.88 3.34-2.19c1.39.45 2.9.2 3.91-.81s1.27-2.52.81-3.91c1.31-.67 2.19-1.91 2.19-3.34zm-11.71 4.2L6.8 12.46l1.41-1.42 2.26 2.26 4.8-5.23 1.47 1.36-6.2 6.77z">
                                        </path>
                                    </g>
                                </svg>
    `
}

function loadUserSVG(notificationId, notificationUserType){
    let userNameDiv = document.getElementById(notificationId + "userNameSvg");
    let svgToLoad = users[notificationUserType];
    userNameDiv.innerHTML += svgToLoad;
}