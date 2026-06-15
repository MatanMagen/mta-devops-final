pipeline {
    agent any

    tools {
        jdk 'jdk25'
        maven 'maven3'
    }

    environment {
        // local checkout-free path to this project - used by Option A (inline Pipeline script)
        PROJECT_DIR = 'C:/Users/ofrik/iCloudDrive/Documents/computer_science/year_4/semester_2/DevOps/devops-gatling-test'
    }

    stages {
        stage('Build') {
            steps {
                dir(PROJECT_DIR) {
                    bat 'mvn -B clean compile'
                }
            }
        }

        stage('Load & Stress Tests') {
            steps {
                dir(PROJECT_DIR) {
                    bat 'mvn -B gatling:test -Dgatling.simulationClass=devops.LoadTestSimulation'
                    bat 'mvn -B gatling:test -Dgatling.simulationClass=devops.StressTestSimulation'
                }
            }
        }

        stage('Max Limit Tests (isolated bursts)') {
            steps {
                dir(PROJECT_DIR) {
                    // each burst size gets its own mvn/JVM invocation so it starts from a clean
                    // connection/queue state - see MAX_LIMIT_REPORT.md for why mixing burst sizes
                    // in one run produces misleading results
                    bat 'mvn -B gatling:test -Dgatling.simulationClass=devops.TomcatMaxLimitSimulation -Dburst.size=1500'
                    bat 'mvn -B gatling:test -Dgatling.simulationClass=devops.TomcatMaxLimitSimulation -Dburst.size=2000'
                    bat 'mvn -B gatling:test -Dgatling.simulationClass=devops.TomcatMaxLimitSimulation -Dburst.size=5000'
                    bat 'mvn -B gatling:test -Dgatling.simulationClass=devops.TomcatMaxLimitSimulation -Dburst.size=10000'
                }
            }
        }
    }

    post {
        always {
            dir(PROJECT_DIR) {
                archiveArtifacts artifacts: 'target/gatling/**', allowEmptyArchive: true
            }
        }
    }
}
