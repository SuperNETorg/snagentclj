(defproject snagentclj "1.0.0"
  :description "SuperNET agent framework for clojure"
  :min-lein-version  "2.0.0"
  :source-paths      ["src/clojure"]
  :java-source-paths ["src/java"]
  :dependencies [
    [org.clojure/clojure "1.6.0"]
    [net.java.dev.jna/jna "4.1.0"]
    [net.java.dev.jna/jna-platform "4.1.0"]
    [com.googlecode.json-simple/json-simple "1.1.1"]
    [org.bouncycastle/bcprov-jdk15on "1.51"]
    [org.clojure/tools.nrepl "0.2.3"]]
  :main "echodemo.app")