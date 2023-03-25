# Twitter Clone

The objetive is to replicated the Social Media Twitter with some upgrades for the user experience.

## :scroll:Table of Contents
* [Phase 0](#hotsprings-phase-0---team-formation-and-features)
    * [Team Members](#hammer-team-members)
    * [Requirements](#bookmark_tabs-requirements)
        * [Entities](#house-entities)
        * [Type of User](#bust_in_silhouette-type-of-user)
        * [User Permissions](#orange_book-user-permissions)
        * [Use Cases](#performing_arts-use-cases)
        * [Non Functional Requirements](#triangular_flag_on_post-non-functional-requirements)
        * [Advanced algorithms](#dart-advanced-algorithms)
* [Phase 1](#triangular_ruler-phase-1---layout)
    * [Screens](#computer-screens)
    * [Navigation Map](#airplane-navigation-map)
* [Phase 2](#satellite-phase-2---web-with-server-generated-html-and-ajax)
    * [Start-up of the application](#black_nib-start-up-of-the-aplication)
        * [Sample User](#closed_lock_with_key-sample-users)
    * [DataBase](#card_index-database)
    * [Diagrams](#flower_playing_cards-diagrams)
    * [Members Participation](#busts_in_silhouette-members-participation---phase-2)
* [Phase 3](#whale-phase-3---add-a-rest-api-to-the-web-application-and-deploy-withdocker)
    * [API REST documentation](#postbox-api-rest-documentation)
    * [Dockerized application execution instructions](#calling-dockerized-application-execution-instructions)
    * [Documentation for building the docker image](#whale2-documentation-for-building-the-docker-image)
    * [Members Participation](#busts_in_silhouette-members-participation---phase-3)


***

## :hotsprings: Phase 0 - Team formation and features

### :hammer: Team Members

| Name                                     | Email                            | Nickname                                           |
| :--------------------------------------: | :------------------------------: | :------------------------------------------------: |
| Sergio de Oro Fernández                  | s.deoro.2021@alumnos.urjc.es     | [@keyland71](https://github.com/keyland71)         |
| Laura María Camino Yuste                 | lm.camino.2020@alumnos.urjc.es   | [@Camyus027](https://github.com/Camyus027)         |
| Antonio José Alanís Bernal               | aj.alanis.2020@alumnos.urjc.es   | [@antonioalanxs](https://github.com/antonioalanxs) |
| Ikár Martínez de Lizarduy Kostornichenko | iv.martinez.2021@alumnos.urjc.es | [@KandV008](https://github.com/KandV008)           |
| Miguel Ángel Sánchez Miranda             | ma.sanchezm.2021@alumnos.urjc.es | [@MiguelAngelSM](https://github.com/MiguelAngelSM) |

To organize the team, we are using [Trello](https://trello.com/b/WpuV8KtM/la-puta-mejor-práctica-de-daw)

### :bookmark_tabs: Requirements

#### :house: Entities

* Tweets
* Hashtags
* User
* Notifications
* Trends

![ER Diagram](./files-to-README/phase0/E-R_Diagram.svg)

#### :bust_in_silhouette: Type of User

* **Anonymous** &rarr; User which has not signed up in the application but still has access to a few functionalities.
* **Registered** &rarr; User who has already signed up in the application and has a wide variety of available functionalities.
* **Admin** &rarr; User who has the overall control of the platform, having the strongest set of permissions

#### :orange_book: User Permissions

| User           | Tweets                                        | People                                  | Hashtags                                        | Notifications |
| :------------: | :-------------------------------------------: | :-------------------------------------: | :---------------------------------------------: | :-----------: |
| **Anonymous**  | Read                                          | Read(See their profile)                 | Read (can search them and read the impressions) | Nothing       |
| **Admin**      | Read, write (their own), detele (every tweet) | Read, delete, write (their own profile) | Read, write                                     | Read          |
| **Registered** | Read, write (their own), delete (their Own)   | Read, write (their own profile)         | Read, write                                     | Read          |

| Permissions  | Affects to                                                                                              |
| ------------ | ------------------------------------------------------------------------------------------------------- |
| **Images**   | All registered users can upload images                                                                  |
| **Graphics** | Admin users can consult graphics with the statistics of the web (Number of new registered users)        |

| Complementary Technologies                                       |
| :--------------------------------------------------------------: |
| Mail Sender                                                      |

#### :performing_arts: Use Cases
<div style="display: flex;
            flex-direction: column;
            text-align: center;">
<small>

![useCase1](./files-to-README/phase0/useCase1.png)
*Figure 1 - Sign Up and Log In*

![useCase2](./files-to-README/phase0/useCase2.png)
*Figure 2 - Admin permissions*

![useCase3](./files-to-README/phase0/useCase3.png)
*Figure 3 - General permissions*

![useCase4](./files-to-README/phase0/useCase4.png)
*Figure 4 - Registered permissions*

![useCase5](./files-to-README/phase0/useCase5.png)
*Figure 5 - User Actions*

</small>
</div>


#### :white_check_mark: User Requirements

| Requirements                                                | Anonymous User     | Registered User    | Admin User         |
| :---------------------------------------------------------: | :----------------: | :----------------: | :----------------: |
| Sign up                                                     | :heavy_check_mark: |                    |                    |
| Log in                                                      |                    | :heavy_check_mark: | :heavy_check_mark: |
| See the preview                                             | :heavy_check_mark: |                    |                    |
| Search (using filters)                                      | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: |
| See tweet information                                       | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: |
| Interact with a tweet (retweet, like and comment)           |                    | :heavy_check_mark: | :heavy_check_mark: |
| Reply to a tweet                                            |                    | :heavy_check_mark: | :heavy_check_mark: |
| Save a tweet                                                |                    | :heavy_check_mark: | :heavy_check_mark: |
| Write a tweet                                               |                    | :heavy_check_mark: | :heavy_check_mark: |
| Delete a tweet                                              |                    | :heavy_check_mark: | :heavy_check_mark: |
| Visualize recommend users                                   | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: |
| Receive notifications                                       |                    | :heavy_check_mark: | :heavy_check_mark: |
| Receive email notifications                                 | :heavy_check_mark: |                    |                    |
| Visualize statistics                                        |                    |                    | :heavy_check_mark: |
| Edit profile                                                |                    | :heavy_check_mark: | :heavy_check_mark: |
| Visualize profile                                           | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: |
| Follow a profile                                            |                    | :heavy_check_mark: | :heavy_check_mark: |
| Visualize tendencies                                        | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: |
| Ban users                                                   |                    |                    | :heavy_check_mark: |
| Unbanned users                                              |                    |                    | :heavy_check_mark: |
| Delete tweets                                               |                    |                    | :heavy_check_mark: |
| Verificate users                                            |                    |                    | :heavy_check_mark: |
| Unverificate user                                           |                    |                    | :heavy_check_mark: |

#### :triangular_flag_on_post: Non Functional Requirements

* Must abide by the law (Of each continent/country in which is it used)
* Supports tweets comprising text and pictures
* Generating the timeline should be fast within half a second (at most)
* Systems must be available at all times (scalability and availability) 
* Data should be durable
* Security must be ensured at all phases
* The size of a tweet is 240 characters at most

#### :dart: Advanced algorithms

* ##### Recommended Systems

    * We will create a SQL query to recommend new profiles to our registered users using collaborative filtering techniques.

* ##### Search

    *  We will create a strong search algorithm able to adapt to the filters that users select to provide an accurate and fast result.

***


## :triangular_ruler: Phase 1 - Layout

### :computer: Screens

<div style="display: flex;
            flex-direction: column;
            text-align: center;">
<small>

![index.html](./files-to-README/phase2/index.png)
*Figure 6 - index.html*

![login.html](./files-to-README/phase2/login.png)
*Figure 7 - login.html*

![signup.html](./files-to-README/phase2/signup.png)
*Figure 8 - signup.html*

![home.html](./files-to-README/phase2/home.png)
*Figure 9 - home.html*

![explore.html](./files-to-README/phase2/explore.png)
*Figure 10 - explore.html*

![explore.html](./files-to-README/phase2/explore-anonimo-3.png)
*Figure 11 - explore-anonymous.html*

![explore.html](./files-to-README/phase2/explore_anonimo-2.png)
*Figure 12 - explore-anonymous-tweets.html*

![bookmarks.html](./files-to-README/phase2/bookmarks.png)
*Figure 13 - bookmarks.html*

![notifications.html](./files-to-README/phase2/notifications.png)
*Figure 14 - notifications.html*

![profile.html](./files-to-README/phase2/profile.png)
*Figure 15 - profile.html*

![profile.html](./files-to-README/phase2/edit-profile.png)
*Figure 16 - edit-profile.html*

![dashboard.html](./files-to-README/phase2/dashboard-1.png)
*Figure 17 - dashboard.html*

![stats.html](./files-to-README/phase2/dashboard-2.png)
*Figure 18 - stats.html*

![write-tweet.html](./files-to-README/phase2/write-tweet.png)
*Figure 19 - write-tweet.html*

![reply-tweet.html](./files-to-README/phase2/reply-tweet.png)
*Figure 20 - reply-tweet.html*

![error.html](./files-to-README/phase1/error_screen.png)
*Figure 21 - error.html*


</small>
</div>

### :airplane: Navigation Map

<div style="display: flex;
            flex-direction: column;
            text-align: center;">
<small>

| Color | User Movement |
| :---: | :-----------: |
| 🔵 | All users |
| 🟢 | Registered Users |
| 🔴 | Admin Users |

#### Main Navigation Map

![navigationMap](./files-to-README/phase2/mapNavi1.png)
*Figure 22 - Navigation Map 1*

#### Navigation Map of the Anonymous User

![navigationMap2](./files-to-README/phase2/mapNavi2.png)
*Figure 23 - Navigation Map 2*

#### Navigation Map of the Admin User

![navigationMap3](./files-to-README/phase2/mapNavi3.png)
*Figure 24 - Navigation Map 3*

#### New user registration and password recovery Navigation Map 

![navigationMap4](./files-to-README/phase2/mapNavi4.png)
*Figure 25 - Navigation Map 4*

</small>
</div>

***

## :satellite: Phase 2 - Web with server-generated HTML and AJAX

### :black_nib: Start-up of the aplication

#### :feet: Steps

1. Check [Requirements](#requirements)
2. Clone the repository

```
git clone https://github.com/CodeURJC-DAW-2022-23/webapp8.git
cd webapp8
```

3. Configure [DataBase](#database-configuration)
4. Configure [IDE](#ide-configuration)
5. Run Application

```
cd ProjectBackEnd\src\main\java\com\TwitterClone\ProjectBackend
javac ProjectBackendApplication
```

6. Go to [localhost URL](https://localhost/8443/)
7. Use the [default users](#closed_lock_with_key-sample-users) to navigate or create a new user

#### DataBase Configuration
- Download [MySQL v.8.0.32](http://dev.mysql.com/downloads/)

| Section | Value |
| :------: | :---: |
| Port | `3306` |
| User name | `Admin` |
| Password | `password` |

- Create a user with name Admin and password "password" with DB admin as user role
- Configure MySQL Server as Windows Service
- Grant full access to the user
- Create a new Schema named twitter in the server using MySQL Workbench

> Note:
> If you have selected to not let the server to run when the system starts you can run the server with Windows R and execute the command "services.msc". In the list you can find MySQL server and run it. 

#### IDE Configuration
- We recommend [IntelliJ IDEA](https://www.jetbrains.com/idea/) but you can use other IDE's
- Install [Maven](https://maven.apache.org/download.cgi) and [Spring](https://spring.io/projects/spring-boot) for your IDE

| Enviroment Variable |
| :-----------------: |
| SPRING_MAIL_PASSWORD=APasswordForTheMail |


#### Requirements

| Requirements |
| :----------: |
| [MySQL v.8.0.32](http://dev.mysql.com/downloads/) |
| [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) |
| [Spring Boot 2.7.4](https://spring.io/projects/spring-boot) |
| [IDE](#ide-configuration) |

#### :closed_lock_with_key: Sample users

| User               | Username      | Password           |
| :----------------: | :-----------: | :----------------: |
| **@admin**         | admin         | *adminpass*        |
| **@user**          | user          | *pass*             |
| **@antonioalanxs** | antonioalanxs | *examplePassword3* |
| **@Keyland71**     | Keyland71     | *examplePassword2* |
| **@ibai**          | ibai          | *ibai*             |
| **@Rubiu5**        | Rubiu5        | *rubius*           |

### :card_index: DataBase

![database](./files-to-README/phase2/DB_diagram.svg)
*Figure 26 - DataBase*

### :flower_playing_cards: Diagrams

![diagram](./files-to-README/phase2/Templates.svg)
*Figure 27 - Class Diagram*

### :busts_in_silhouette: Members Participation - Phase 2

#### Sergio De Oro Fernández

* Creator and controller of all notifications, his great creativity has allowed logical and ingenious solutions.

| #   | Commit      |     | #   | File |
| :-: | :---------: | :-: | :-: | :--: |
| 1º  | [Fix tweet's buttons visual](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/3768cc13aa91e8e07bacd1ffe06e3fd8dd286d56) | | 1º | [Navigation Controller](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Controller/NavigationController.java)           |
| 2º  | [Create and Delete Like notifications](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/1ea02a266549d15e627e86671cf6ad6bb3810f9) | |2º       | [Notification Controller](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Controller/NotificationController.java) |
| 3º  | [Add create and delete notification system](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/e3544ed8d6c275f3ca3955afef7cf071ceb8359f) | | 3º | [Notification Service](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Service/NotificationService.java) |
| 4º  | [Show all user-type svg](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/d53cbd016b685d91330d00e712c598f91442cc7d) | | 4º | [Tweet Controller](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Controller/TweetController.java) |
| 5º  | [Add showing notifications](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/d19088352f881d82a8ee3d772be41e93365c76df) | | 5º | [Load Notifications Assets](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/resources/static/assets/js/loadNotificationAssets.js) |

#### Laura María Camino Yuste

* Our security scientist has prepared all the security of our application, together with all the user and session management logic.

| #   | Commit      |     | #   | File |
| :-: | :---------: | :-: | :-: | :--: |
| 1º  | [Add Signup Logic](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/659bfadd4a7b28969d80f19012322817a0685a41) | | 1º | [Web Security Configuration](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Security/WebSecurityConfig.java) |
| 2º  | [Fix Login Bug](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/a427e163f56e2ae87f7e1e11b1ec9551d1ef38c7) | |2º | [User Details Service](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/userManagement/RepositoryUserDetailsService.java) |
| 3º  | [Add verify account through email](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/f790a17eef5b6e652aa4c775882168751855a3ba) | | 3º | [SingUp Controller](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Controller/UserSignupController.java) |
| 4º  | [Add Change Password functionality](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/7dcddbd22abe9fbd79514002821fefcab2454aa8) | | 4º | [User Service](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/userManagement/UserService.java) |
| 5º  | [Fix CSRF management](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/424b562089ab081a17524988596943d4df3d7e87) | | 5º | [ResetPasswordController](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Controller/ResetPasswordController.java) |

#### Antonio José Alanís Bernal

* Main designer artist, he has created the entire visual section of the web application, together with all the logic referring to the profiles of the different users.

| #   | Commit      |     | #   | File |
| :-: | :---------: | :-: | :-: | :--: |
| 1º  | [Add AJAX to profile page](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/d79cd4872900c9462e98d18cf0de728306a14f95) | | 1º      | [Ajax](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/resources/static/assets/js/ajax.js)           |
| 2º  | [Add AJAX to follow page](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/5db370a45051cebf7f91a6c9fcea4f08a7131969)  | |2º       | [Navigation Controller](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Controller/NavigationController.java)          |
| 3º  | [Add user's tweets to profile](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/1acd14cce631523ee2e558de35f97270acb14c11) | | 3º     | [Profile Controller](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Controller/ProfileController.java)         |
| 4º  | [Add edit profile logic and refactor template](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/bcb1b65cac838afed0f26596813e54a0211bc043) | | 4º | [User Repository](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Repository/UserRepository.java)        |
| 5º  | [Show user profile](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/14bc14bd048baae34b9bd13c837b3673413a0495) | | 5º     | [Profile View](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/resources/static/templates/profile.html)       |

#### Ikár Vladislav Martínez de Lizarduy Kostornichenko

* Worker of the bridge between the front and the backend of the web application, together with the documentation of our website.

| #   | Commit      |     | #   | File |
| :-: | :---------: | :-: | :-: | :--: |
| 1º  | [Conect Front and Back end for trends](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/6a243d67b62d7d629f815f250b6273d26885f0d0) | | 1º      | [Information Manager](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Model/MustacheObjects/InformationManager.java)           |
| 2º  | [Add Navigation with Spring](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/fa2ef6257095eab061411c2ebcaafb323a126e19) | |2º       | [Ajax](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/resources/static/assets/js/ajax.js)          |
| 3º  | [Add tweet creation](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/4fdaf55a26c9a12550aea8607ffc8d0b42422ab8) | | 3º     | [Navigation Controller](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Controller/NavigationController.java)         |
| 4º  | [Add to be able to see tweets associated with a hashtag](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/7cc6a246259b5bbc1e6ba1af4b55f316cc375699) | | 4º | [Image Controller](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Controller/ImageController.java)        |
| 5º  | [Fix problem with AJAX](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/ee6628227241561cc08f54e56400dc6484ad7f78) | | 5º     | [Hashtag Controller](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Controller/HashtagController.java)       |

#### Miguel Ángel Sánchez Miranda

* Manager of everything related to the Database, being the one who most dominates this aspect with his great ability to perform queries.

| #   | Commit      |     | #   | File |
| :-: | :---------: | :-: | :-: | :--: |
| 1º  | [Data Base migration to MySQL](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/36ab7219f97bfc0f8d08d7fd263e11e36a997c73) | | 1º      | [Database Initializer](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Service/DBInitializer.java)           |
| 2º  | [Add Admin dashboard mustache and statics](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/5bb8d61e3b2fddda4c72f40f7e227fa741f5912e) | |2º       | [Tweet Repository](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Repository/TweetRepository.javaa)        |
| 3º  | [Rework DataBase to fix circular references](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/d52e49cab7f1d11ef12e9fd7b7aa800aaff93f3a) | | 3º     | [Admin Dashboard View](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/resources/static/templates/admin-dashboard.html)         |
| 4º  | [Add new queries to Tweet](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/f2413dcc7beb1a50c471210f36c1aea6be090d45) | | 4º | [Tweet](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Model/Tweet.java)        |
| 5º  | [Update Hashtag](https://github.com/CodeURJC-DAW-2022-23/webapp8/tree/34066d1d40a06962bbd767c14ebf71edb4368347) | | 5º     | [Tweet Service](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Service/TweetService.javajs)       |

***

## :whale: Phase 3 - Add a REST API to the web application and deploy withDocker

### :postbox: API REST documentation

| URLs | [File]() | [In Production]() | [For Developers]() |
| :--: | :--: | :-----------: | :------------: | 

### :calling: Dockerized application execution instructions

### :whale2: Documentation for building the docker image

### :busts_in_silhouette: Members Participation - Phase 3

#### Sergio De Oro Fernández

* Creator and controller of all notifications, his great creativity has allowed logical and ingenious solutions.

| #   | Commit      |     | #   | File |
| :-: | :---------: | :-: | :-: | :--: |
| 1º  |  | | 1º      |         |
| 2º  |  | | 2º      |         |
| 3º  |  | | 3º      |         |
| 4º  |  | | 4º      |         |
| 5º  |  | | 5º      |         |

#### Laura María Camino Yuste

* Our security scientist has prepared all the security of our application, together with all the user and session management logic.

| #   | Commit      |     | #   | File |
| :-: | :---------: | :-: | :-: | :--: |
| 1º  | [Finish Rest Security Configuration](https://github.com/CodeURJC-DAW-2022-23/webapp8/commit/8b7fa0564473516e405dac9445fd70ad314bf41a) | | 1º      | [RestSecurityConfig](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Security/RestSecurityConfig.java)        |
| 2º  | [Add UserSignUpRestController](https://github.com/CodeURJC-DAW-2022-23/webapp8/commit/25b3ace3b9ae6b0d9dd027a421dcbc3da0651f5a) | | 2º      | [UserSignUpRestController](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Controller/RestController/SearchRestController.java)        |
| 3º  | [Add SearchRestController](https://github.com/CodeURJC-DAW-2022-23/webapp8/commit/16e69880c03dc914c9fcfcdbe7f111e5b2d26f50) | | 3º      | [SearchRestController](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Controller/RestController/SearchRestController.java)        |
| 4º  | [Add AdminDashBoardRestController](https://github.com/CodeURJC-DAW-2022-23/webapp8/commit/0a746921c17c73c295095984da17c15c489aa4d3) | | 4º      |  [AdminDashboardRestController](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Controller/RestController/AdminDashboardRestController.java)       |
| 5º  | [Add ResetPasswordRestController](https://github.com/CodeURJC-DAW-2022-23/webapp8/commit/ce965349d4472a58b4f8336270d50702b526131b) | | 5º      | [ResetPasswordRestController](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Controller/RestController/ResetPasswordRestController.java)        |

#### Antonio José Alanís Bernal

* Main designer artist, he has created the entire visual section of the web application, together with all the logic referring to the profiles of the different users.

| #   | Commit      |     | #   | File |
| :-: | :---------: | :-: | :-: | :--: |
| 1º  |  | | 1º      |         |
| 2º  |  | | 2º      |         |
| 3º  |  | | 3º      |         |
| 4º  |  | | 4º      |         |
| 5º  |  | | 5º      |         |

#### Ikár Vladislav Martínez de Lizarduy Kostornichenko

* Worker of the bridge between the front and the backend of the web application, together with the documentation of our website.

| #   | Commit      |     | #   | File |
| :-: | :---------: | :-: | :-: | :--: |
| 1º  |  | | 1º      |         |
| 2º  |  | | 2º      |         |
| 3º  |  | | 3º      |         |
| 4º  |  | | 4º      |         |
| 5º  |  | | 5º      |         |

#### Miguel Ángel Sánchez Miranda

* Manager of everything related to the Database, being the one who most dominates this aspect with his great ability to perform queries.

| #   | Commit      |     | #   | File |
| :-: | :---------: | :-: | :-: | :--: |
| 1º  | [Add Profile Methods for API](https://github.com/CodeURJC-DAW-2022-23/webapp8/commit/d8c17f1d925f6888b023e54daf9719c31d7d5393) | | 1º | [RestTweetController](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Controller/RestController/RestTweetController.java)       |
| 2º  | [Add Post methods to RestTweetController](https://github.com/CodeURJC-DAW-2022-23/webapp8/commit/dc0d39ec73e47159ad8472f97fb5efc480e87d6e) | | 2º      | [RestProfileController ](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Controller/RestController/RestProfileController.java)         |
| 3º  | [Add GET methods to RestTweetController](https://github.com/CodeURJC-DAW-2022-23/webapp8/commit/13744c51e08907a52d8386c25bc7790c46cd2aa3) | | 3º      | [AdminDashboardRestController](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Controller/RestController/AdminDashboardRestController.java)       |
| 4º  | [Add PUT methods to RestTweetController](https://github.com/CodeURJC-DAW-2022-23/webapp8/commit/19155004768819853760d550ab71021e870618e1) | | 4º      | [Tweet](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Model/Tweet.java)        |
| 5º  | [Create JsonViews](https://github.com/CodeURJC-DAW-2022-23/webapp8/commit/cbf5175dab165666b5afe1f7629d33d57d9a3a8f) | | 5º      | [TweetController](https://github.com/CodeURJC-DAW-2022-23/webapp8/blob/main/ProjectBackEnd/src/main/java/com/TwitterClone/ProjectBackend/Controller/TweetController.java)        |

***
