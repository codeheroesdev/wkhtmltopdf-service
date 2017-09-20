# wkhtmltopdf-service
Simple stateless service exposing REST API for generating PDF using wkhtmltopdf. There is configured docker in build.sbt file
containing installed Java 8 and wkhtmltopdf.

Sample request:
```
curl -X POST \
  http://localhost:8080/generate \
  -H 'content-type: application/json' \
  -d '{
  "orientation": "portrait",
  "pageSize": "A4",
  "margin": {
  	"left": "10mm",
  	"right": "10mm",
  	"top": "10mm",
  	"bottom": "10mm"
  },
  "content": "<html><head></head><body><h1>Hi!</h1><p>This is your first pdf file.</p></body></html>"
  }'
 ```
