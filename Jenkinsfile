pipeline {
   agent any
   environment {
       registry = "magalixcorp/k8scicd"
       GOCACHE = "/tmp"
   }
   stages {
       stage('Build') {
           agent {
               docker {
                   image 'bitriseio/docker-bitrise-base'
               }
           }
           steps {
               sh './gradlew compileDebugSources'
           }
       }
       stage('Test') {
           agent {
               docker {
                   image 'bitriseio/docker-bitrise-base'
               }
           }
           steps {
               sh './gradlew testDebugUnitTest testDebugUnitTest'
           }
       }
   }
}