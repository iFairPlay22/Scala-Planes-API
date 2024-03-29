pipeline {
    // https://github.com/sbt/docker-sbt
    agent { docker {
        image 'ewenbouquet/jenkins-img-scala:latest'
        args  '--net="ewenbouquet/jenkins-network"'
    } }
    environment {
        PLANES_API_HOST                 = '0.0.0.0'
        PLANES_API_PORT                 = '8080'
        BROKER_PLANES_TOPIC             = 'ewenbouquet_planes'
        BROKER_BOOTSTRAP_SERVERS        = 'broker:9092'
        BROKER_SCHEDULER_INITIAL_DELAY  = '5 seconds'
        BROKER_SCHEDULER_REFRESH_DELAY  = '1 minute'
        DB_HOST                         = 'postgres'
        DB_PORT                         = '5432'
        DB_KEYSPACE                     = 'db_ewen'
        DB_USER                         = 'admin'
        DB_PASSWORD                     = 'root'
    }
    stages {
        stage('Compilation') {
            steps {
                echo 'Compiling...'
                sh 'sbt clean compile'
            }
        }
        stage('Format') {
            steps {
                echo 'Checking format...'
                sh 'sbt scalafmtCheckAll'
            }
        }
        stage('Domain tests') {
            steps {
                echo 'Domain testing...'
                sh 'sbt domain/test'
            }
        }
        stage('DB tests') {
            steps {
                echo 'DB testing...'
                sh 'sbt db/test'
            }
        }
        stage('API tests') {
            steps {
                echo 'API testing...'
                sh 'sbt api/test'
            }
        }
        stage('Broker tests') {
            steps {
                echo 'Broker testing...'
                sh 'sbt brokerProducer/test'
                sh 'sbt brokerConsumer/test'
            }
        }
        stage('Newman tests') {
            steps {
                echo 'Newman testing...'
                // TODO
            }
        }
    }
}