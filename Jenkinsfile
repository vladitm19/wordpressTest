pipeline {
  agent any
  environment {
    SONAR_TOKEN = credentials('SONAR_TOKEN')
  }
  stages {
      stage('Regression') {
        steps {
          sh 'chmod +x gradlew'
          sh ' ./gradlew clean executeFeatures -PenvId="QA01" -PcucumberOptions="@Regression"'
        }
        post {
            always {
                cucumber buildStatus: 'UNSTABLE',
                        failedFeaturesNumber: 1,
                        failedScenariosNumber: 1,
                        skippedStepsNumber: 1,
                        failedStepsNumber: 1,
                        reportTitle: 'My report',
                        fileIncludePattern: 'build/cucumber/cucumber.json',
                        sortingMethod: 'ALPHABETICAL',
                        trendsLimit: 100
            }
        }
      }
  }
  post {
      always {
        emailext body: 'Hi Tojoris!, this is the link!\nClick here: $BUILD_URL\nHave a nice day!!!',
        subject: '$BUILD_STATUS! - Pipeline status and link of the pipeline log',
        to: 'todolytojoris@gmail.com'
      }
    }
}