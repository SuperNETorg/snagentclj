(ns echodemo.app
  (:gen-class)
  (:require [framework.core :as framework]))

(deftype echodemo-agent []
  framework/sn-agent-interface
  (agent-name [x] "echodemo")
  (agent-methods [x] ["echo"])
  (agent-pubmethods [x] ["echo"])
  (agent-authmethods [x] ["echo"])

  ;; info is AgentInfo struct and args is a map with agent start args
  ;; return bitmap of disabled methods
  (agent-register [x info args] 0)

  ;; initialize from JSON object. process JSON, return JSON
  (agent-process-register [x info args] (set! (.-allowremote info) true) {"result" "echodemo init"})

  (agent-idle [x info] 0)
  (agent-shutdown [x info retcode] 0)

  ;; process incoming request. process JSON, return JSON
  (agent-process-request [x info request]
    (let [error (get request "error")
          result (get request "result")
          method (get request "method")]
      (if (or (some? result) (some? error))
        {"result" "completed"}
        (if (some? method)
          (if (= method "echo")
            {"result" (get request "echostr")}
            nil)
          (do (println "request (" request ") has not method") nil)))))
)

(defn -main
  "echodemo agent for SuperNET"
  [& args]
  (println "Hello from echodemo")
  (println "The numbers are " args)
  (framework/init (echodemo-agent.) args (System/out)))