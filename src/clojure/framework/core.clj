(ns framework.core
  (:import [snagentj AgentInterface Framework]
           [org.json.simple JSONObject]))

(defn to-json-object
  "Convert clojure map to JSONObject"
  [a]
  (if (some? a)
    (JSONObject. a)
    nil))

(defn from-json-object
  "Convert JSONObject to clojure map"
  [a]
  (into {} a))

(defprotocol sn-agent-interface
  (agent-name [x])
  (agent-methods [x])
  (agent-pubmethods [x])
  (agent-authmethods [x])
  (agent-register [x info args])
  (agent-process-register [x info args])
  (agent-idle [x info])
  (agent-shutdown [x info retcode])
  (agent-process-request [x info args]))

(defn sn-agent
  ""
  [agent]
  (proxy [AgentInterface] []
    (getName [] (agent-name agent))
    (getMethods [] (into-array String (agent-methods agent)))
    (getPubmethods [] (into-array String (agent-pubmethods agent)))
    (getAuthmethods [] (into-array String (agent-authmethods agent)))
    (register [info args] (agent-register agent info (from-json-object args)))
    (processRegister [info args] (to-json-object (agent-process-register agent info (from-json-object args))))
    (idle [info] (agent-idle agent info))
    (process [info request] (to-json-object (agent-process-request agent info (from-json-object request))))
    (shutdown [info retcode] (agent-shutdown agent info retcode))
))

(defn init
  "Initialize SuperNET agent framework"
  [agent args logger]
  (Framework/init (sn-agent agent) (into-array String args) logger))