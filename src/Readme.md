# Reinforcement Learning, Q-Learning - Scala

Proyecto que corresponde al desarrollo de un algortimo Q-Learning usando el lenguaje de programación Scala.

Este algoritmo hará que el agente aprenda una serie de normas que le lleve a tomar la decisión óptima (acción) en cada estado en particular.

El objetivo del agente es maximizar su recompensa total. Lo hace sumando la máxima recompensa alcanzable, en estados futuros, a la recompensa por alcanzar su estado actual, influyendo eficazmente en la acción actual por la futura recompensa potencial. Esta recompensa potencial es una suma ponderada de la esperanza matemática de las recompensas de los futuros pasos empezando desde el estado actual.

El peso de un paso en un estado {\displaystyle \Delta {t}}{\displaystyle \Delta {t}} pasos en el futuro se calcula como {\displaystyle \gamma ^{\Delta {t}}}{\displaystyle \gamma ^{\Delta {t}}}. {\displaystyle \gamma }\gamma  (el factor de descuento) es un número entre 0 y 1 ({\displaystyle 0\leq \gamma \leq 1}{\displaystyle 0\leq \gamma \leq 1}) y evalúa las recompensas recibidas anteriormente con un valor mayor que las recibidas posteriormente.{\displaystyle \gamma }{\displaystyle \gamma} también puede ser interpretada como la probabilidad de tener éxito (o sobrevivir) en cada paso {\displaystyle \Delta {t}}{\displaystyle \Delta {t}}.

El algoritmo, por tanto, tiene una función que calcula la calidad de una combinación estado-acción:

{\displaystyle \displaystyle Q:S\times A\rightarrow \mathbb {R} }{\displaystyle \displaystyle Q:S\times A\rightarrow \mathbb {R} }.
Antes que comience el aprendizaje, {\displaystyle \displaystyle Q}{\displaystyle \displaystyle Q} se inicializa a un valor arbitrario constante. Después, en cada tiempo {\displaystyle \displaystyle t}{\displaystyle \displaystyle t} el agente selecciona una acción {\displaystyle \displaystyle a_{t}}{\displaystyle \displaystyle a_{t}}, observa una recompensa {\displaystyle r_{t}}{\displaystyle r_{t}}, introduce un estado nuevo {\displaystyle s_{t+1}}{\displaystyle s_{t+1}} (Que depende del estado anterior {\displaystyle s_{t}}{\displaystyle s_{t}} y de la acción seleccionada), y {\displaystyle \displaystyle Q}{\displaystyle \displaystyle Q} se actualiza. 

{\displaystyle \displaystyle Q^{new}(s_{t},a_{t})\leftarrow (1-\alpha )\cdot \underbrace {Q(s_{t},a_{t})} _{\text{old value}}+\underbrace {\alpha } _{\text{learning rate}}\cdot \overbrace {{\bigg (}\underbrace {r_{t}} _{\text{reward}}+\underbrace {\gamma } _{\text{discount factor}}\cdot \underbrace {\max _{a}Q(s_{t+1},a)} _{\text{estimate of optimal future value}}{\bigg )}} ^{\text{learned value}}}

Donde {\displaystyle r_{t}}{\displaystyle r_{t}} es la recompensa recibida al pasar del estado {\displaystyle s_{t}}{\displaystyle s_{t}} al estado {\displaystyle s_{t+1}}{\displaystyle s_{t+1}}, y {\displaystyle \alpha }{\displaystyle \alpha } es el índice de aprendizaje ({\displaystyle 0<\alpha \leq 1}{\displaystyle 0<\alpha \leq 1}).

Un episodio del algoritmo termina cuando el estado {\displaystyle s_{t+1}}{\displaystyle s_{t+1}}es un estado final o terminal.

Para todos los estados finales {\displaystyle \displaystyle s_{f}}{\displaystyle \displaystyle s_{f}}, {\displaystyle \displaystyle Q(s_{f},a)}{\displaystyle \displaystyle Q(s_{f},a)} nunca se actualiza, pero se fija al valor de recompensa {\displaystyle r}{\displaystyle r} observado para el estado {\displaystyle s_{f}}{\displaystyle s_{f}}.