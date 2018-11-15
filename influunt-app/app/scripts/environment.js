'use strict';
angular.module('environment', [])

.constant('APP_ROOT', "http://localhost/api/api/v1")

.constant('ENV', "development")

.constant('PLACES_API', {
	"baseUrl": "http://egocet.prefeitura.sp.gov.br:8280/cet/geo"
})

.constant('MAP', {
	"url": "http://cetsp1.cetsp.com.br:10084/geoserver/cetmdc/wms?tiled=true",
	"options": {
		"layers": [
			"cetmdc:mdcViario_lg",
			"cetmdc:mdcRotulos_lg"
		],
		"transparent": true,
		"format": "image/png8"
	}
})

.constant('MQTT_ROOT', {
	"url": "localhost",
	"port": 1884
})

.constant('ROOT_API_SMEE', "http://appprod.cetsp.com.br/smee.webapicentraltempofixo/api")
;