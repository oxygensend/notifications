The service is fully customizable based on your needs, in order for it to work properly, you need to set the following configuration and provide it to your Spring application, for example: `java --jar target/notifications.jar --Dspring.additional-config.location=config/config-dev.yml`
```yaml
spring:
  profiles:
    active: # active functionalities separated by commas, possible values: (rest,mail,sms,whatsapp,internal,kafka), eg. rest,mail,kafka 
  data:
    mongodb:
      uri: # database uri

eureka:
  client:
    enabled: true # if eureka is used as a service discovery registry

notifications:
  notifications:
    store-in-database: true # if notification should be store in database default: true
    auth-enabled: true # if authentication and authorization enabled, default: true
  services: # list of whitelisted services eg., ignore if auth-enabled: false
    - "profile-service"
    - "messaging-service"
  secret: # hashed password with SHA-256 algorithm, ignore if auth-enabled: false

  mail: # only if mail profile enabled
    email-from: # email from which mails will be sent
    host: # mail server host
    port: # mail server port
    protocol: smtp

  twilio: # only if sms profile enabled
    account-sid: # twilio account sid
    auth-token: # twilio authentication token
    from-phone-number: # twilio phone number

  whatsapp: # only if whatsapp profile enabled
    api-key: # whatsapp api key
    phone-number-id: # whatsapp phone number id

kafka: # only if kafka profile enabled
  application-id: # application id eg. notification-service
  bootstrap-servers: # bootsrap server eg. localhost:29092 
  security-protocol: # security protcol eg. PLAINTEXT
  sasl-jaas-config:  # sasl eg. org.apache.kafka.common.security.scram.ScramLoginModule required username="dev" password="dev123" # sasl
  topic: # topic name .eg. outsource-me-api-notifications 
  retry:
    max-retries: 5 # max retries default: 5
    back-off-period: 500 # back off period default: 500
    backoff-period-service-unavailable: 5000 #  default 5000
  retry-backoff-ms: 1000 # default 1000
  ssl:
    enabled: false # default false
  auto-offset-reset: earliest # default earliest


```