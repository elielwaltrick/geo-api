import NativePackagerHelper._

name := """play-geo-coleta"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  guice,
  javaJdbc,
  javaJpa,
  ehcache,
  javaWs,
  filters,
  "org.hibernate" % "hibernate-core" % "5.1.0.Final",
  "org.hibernate" % "hibernate-envers" % "5.1.0.Final",
  "org.hibernate" % "hibernate-spatial" % "5.1.0.Final",
  "org.postgresql" % "postgresql" % "9.4-1202-jdbc4",
  "com.vividsolutions" % "jts" % "1.13",
  "org.gdal" % "gdal" % "2.2.0",
  "net.lingala.zip4j" % "zip4j" % "1.3.2",
  "com.lowagie" % "itext" % "2.1.7",
  "org.olap4j" % "olap4j" % "1.2.0",
  "net.sf.jasperreports" % "jasperreports" % "5.6.0",
  "xml-apis" % "xml-apis" % "1.4.01",
  "com.amazonaws" % "aws-java-sdk-s3" % "1.10.1",
  "org.apache.commons" % "commons-lang3" % "3.7",
  "org.apache.commons" % "commons-csv" % "1.5",
  "com.fasterxml.jackson.datatype" % "jackson-datatype-hibernate5" % "2.8.10",
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % "2.8.10",
  "com.bedatadriven" % "jackson-datatype-jts" % "2.4",
  
  "org.geotools" % "gt-epsg-hsql" % "18.4",
  "org.geotools" % "gt-api" % "18.4",
  "org.geotools" % "gt-main" % "18.4",
  "org.geotools" % "gt-referencing" % "18.4",
  "org.geotools" % "gt-geojson" % "19.1",
  
  "com.googlecode.json-simple" % "json-simple" % "1.1",
  "org.jdom" % "jdom2" % "2.0.6",
  
  "javax.media" % "jai_core" % "1.1.3" from "http://download.osgeo.org/webdav/geotools/javax/media/jai_core/1.1.3/jai_core-1.1.3.jar",
  "javax.media" % "jai_codec" % "1.1.3" from "http://download.osgeo.org/webdav/geotools/javax/media/jai_codec/1.1.3/jai_codec-1.1.3.jar",
  "javax.media" % "jai_imageio" % "1.1" from "http://download.osgeo.org/webdav/geotools/javax/media/jai_imageio/1.1/jai_imageio-1.1.jar",
 
)

resolvers += "geosolutions" at "http://maven.geo-solutions.it/"
resolvers += "osgeo" at "http://download.osgeo.org/webdav/geotools/"
resolvers += "boundless" at "https://repo.boundlessgeo.com/main/"
resolvers += "Local Maven" at Path.userHome.asFile.toURI.toURL + ".m2/repository"


// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test

// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

fork in run := true

EclipseKeys.projectFlavor := EclipseProjectFlavor.Java           // Java project. Don't expect Scala IDE
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources)  // Use .class files instead of generated .scala files for views and routes

PlayKeys.externalizeResources := false

