import grails.util.Environment
import io.swagger.models.Scheme
/* IF YOU WANT TO USE AN EXTERNAL ENVIRONMENT/DATABASE
   Configure a file in the below directory given the environment
   -development-config.groovy ( building, running the application)
   -test-config.groovy (integrationTests, tests, ideally should be the same as development locally)
   -production-config.groovy ( production level )
 */
String externalConfigDir = "~/.grails/hpo_web"
grails.config.locations = [
    "${externalConfigDir}/${Environment.current.name}-config.groovy"
]

grails {
    profile = 'web'
    codegen {
        defaultPackage = 'hpo.api'
    }
    spring {
        transactionManagement {
            proxies = false
        }
    }
    gorm {
        reactor {
            events = false
        }
    }

}

info {
    app {
        name = '@info.app.name@'
        version = '@info.app.version@'
        grailsVersion = '@info.app.grailsVersion@'
    }
}

spring {
    main {
        main['banner-mode'] = 'off'
    }
    groovy {
        template {
            template['check-template-location'] = false
        }
    }
}

endpoints {
    enabled = false
    jmx {
        enabled = true
    }
}

grails {
    mime {
        disable {
            accept {
                header {
                    userAgents = [
                        'Gecko',
                        'WebKit',
                        'Presto',
                        'Trident'
                    ]
                }
            }
        }
        types {
            json = [
                'application/json',
                'text/json'
            ]
            hal = [
                'application/hal+json',
                'application/hal+xml'
            ]
            xml = [
                'text/xml',
                'application/xml'
            ]
            atom = 'application/atom+xml'
            css = 'text/css'
            csv = 'text/csv'
            js = 'text/javascript'
            rss = 'application/rss+xml'
            text = 'text/plain'
            all = '*/*'
        }
    }
    /*  Uncomment to use Grails debug console

    plugin {
      console{
        csrfProtection{
          enabled = false
        }
      }
    }*/

    urlmapping {
        cache {
            maxsize = 1000
        }
    }
    controllers {
        defaultScope = 'singleton'
    }
    converters {
        encoding = 'UTF-8'
    }
    cors {
        enabled = true
    }
    resources {
        pattern = '/**'
    }
}

hibernate {
    cache {
        queries = false
        use_second_level_cache = false
        use_query_cache = false
    }
}

dataSource {
    pooled = true
    jmxExport = true
    driverClassName = 'org.h2.Driver'
    username = 'sa'
    password = ''
}

environments {
    development {
        dataSource {
          dbCreate = 'update'
          url = "jdbc:h2:./build/devDb;IGNORECASE=TRUE;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE;MODE=MYSQL"
          grails.dbconsole.enabled = true
          grails.dbconsole.urlRoot = '/admin/dbconsole'
        }
      }
    test {
        dataSource {
          dbCreate = 'update'
          url = "jdbc:h2:./build/testDb;IGNORECASE=TRUE;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE;MODE=MYSQL"
          grails.dbconsole.enabled = true
          grails.dbconsole.urlRoot = '/admin/dbconsole'
        }
    }
    production {
        dataSource {
            dbCreate = 'none'
            url = 'jdbc:h2:${externalConfigDir}/prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE'
            properties {
                jmxEnabled = true
                initialSize = 5
                maxActive = 50
                minIdle = 5
                maxIdle = 25
                maxWait = 10000
                maxAge = 600000
                timeBetweenEvictionRunsMillis = 5000
                minEvictableIdleTimeMillis = 60000
                validationQuery = 'SELECT 1'
                validationQueryTimeout = 3
                validationInterval = 15000
                testOnBorrow = true
                testWhileIdle = true
                testOnReturn = false
                jdbcInterceptors = 'ConnectionState'
                defaultTransactionIsolation = 2
            }
        }
    }
}

swagger {
  info {
    description = "HPO Swagger API Documentation"
    version = "1.5.0"
    title = "HPO REST API"
    termsOfServices = ""
    contact {
      name = "Michael Gargano"
      url = "https://github.com/TheJacksonLaboratory/hpo-web/"
      email = "michael.gargano@jax.org"
    }
    license {
      name = ""
      url = "https://github.com/TheJacksonLaboratory/hpo-web/blob/master/LICENSE.md"
    }
  }
  schemes = [Scheme.HTTP]
  consumes = ["application/json"]
}
