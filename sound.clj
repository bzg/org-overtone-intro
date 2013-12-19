
(let [n (quote 3)
      m (quote 3)]
(-> n inc (+ m)))

(let [x (quote ("simple" "list"))]
(map clojure.string/upper-case x))

(use 'overtone.core)
;; (use 'overtone.studio)
;; (use 'overtone.examples.midi.keyboard)

(boot-external-server)

(use 'overtone.inst.piano)

(piano 40)

(doseq [note (chord :C3 :major)] (piano note))

(doseq [note (chord :E3 :minor)] (piano note))

(doseq [note (chord :A3 :minor)] (piano note))

(defn play-chord [chord]
  (doseq [note chord] (piano note)))

(play-chord (chord :A3 :minor))

(let [time (now)]
  (at time (play-chord (chord :C3 :major)))
  (at (+ 1000 time) (play-chord (chord :C3 :major7)))
  (at (+ 2000 time) (play-chord (chord :E3 :minor)))
  (at (+ 3000 time) (play-chord (chord :A2 :minor))))

(defsynth bar [freq 440]
  (out 0 (sin-osc freq)))

(bar 500)
(kill bar)
(stop)

(definst beep [note 60]
  (let [sound-src (sin-osc (midicps note))
        env (env-gen (perc 0.01 1.0) :action FREE)] ; sam uses :free
    (* sound-src env)))

(beep 60)

(defsynth pad1 [freq 110 amp 1 gate 1 out-bus 0]
  (out out-bus
       (* (saw [freq (* freq 1.01)])
          (env-gen (adsr 0.01 0.1 0.7 0.5) :gate gate :action FREE))))

(pad1)
(stop)

(for [i (range 200)] (at (+ (now) (* i 20)) (beep i)))

(map piano [60 63 67])
(map piano (map note [:C3 :E4 :G4]))
(map piano (map note [:C#5 :E4 :G4]))
(map piano (map note [:Cb2 :E4 :G4]))

(definst steel-drum [note 60 amp 0.8]
  (let [freq (midicps note)]
    (* amp
       (env-gen (perc 0.01 0.2) 1 1 0 1 :action FREE)
       (+ (sin-osc (/ freq 2))
          (rlpf (saw freq) (* 1.1 freq) 0.4)))))

(steel-drum (note :E3))
(map steel-drum (map note [:E3 :D#4]))

(def acc (sample "/home/guerry/Downloads/120931__juskiddink__accordion-pad1.wav"))
(def noa (sample "/home/guerry/Downloads/126724__brunovianna__noaa-19-2011-08-05-14-31-39.wav"))

(let []
  (noa)
  (Thread/sleep 3000)
  (acc)
  (Thread/sleep 2000)
  (piano (note :Cb3))
  (piano 68))

(stop)

(def snare (sample (freesound-path 26903)))
(snare)
(def clic (sample (freesound-path 406)))
(clic)
(def steam (sample (freesound-path 30628)))
(steam)
(def clap (sample (freesound-path 48310)))
(clap)
(def clap2 (sample (freesound-path 132676)))
(clap2)
(def boom (sample (freesound-path 80401)))
(boom)
