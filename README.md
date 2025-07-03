# NutriTrack
A nutrition tracking mobile application

## Description
The application utilises MVVM architecture to ensure clean code separation, Gemini API for data analysis, Room Database for efficient data storage and Fruityvice API to fetch nutritional information of fruits. The application works only works for users who have their details (except their name and password) that was preloaded from the initial CSV into the room database.

Welcome page: The initial page of the app

Login page: The user selects their user ID and their password. If they have not yet registered, they can navigate to the registration page

Registration page: User inputs their user ID and phone number in order to verify their identity as well as adding their name and password

Food intake questionnaire page: a page containings several questions which the answered are stored in the database

Home page: Basic home page that displays their food quality score which is calculated based on the preloaded information in the intial CSV. Also allows users to edit their questionnaire answers.

Insights page: A visual representation of their food score based on different categories, scores are initially preloaded from the CSV. Contains a 'share with someone' button allowing the user to share their score. The 'improve my diet' button simply takes the user to the NutriCoach page.

NutriCoach page: The table allows users to search a fruit and retrieve key information such as the amount of sugar and fats. This data is obtained dynamically from Fruityvice API based on the user query. The 'motivational message (AI)' button utilises Gemini API by sending the user's fruit score and provides motivational messages to encourage the user to increase their fruit intake. Each response is stored in the database and can be viewed by clicking on the 'show all tips' button.

Settings page: Contains account information as well as a 'logout' which takes the user back to the login page and 'clinician login' button which will take them to the clinician login page.

Clinician login page: A login page for clinicians to access the clinician dashboard, requires a predefined key. 

Clinician dashboard page: This page allows the user to gain an insight into the overall nutritional data of all users combined. The table provides the average HEIF scores of males and females. The 'find data pattern' button utilises Gemini API to analyse all user scores and identify patterns within the dataset. The 'exit dashboard' button simply logs the user out from the clinician role and redirects them to the settings page. 




