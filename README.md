### Justifications-Enumeration

Enumerating Justifications via Replicated Driver and Positive Resolution

A replication driver, which discover justification from explored justifications. through cheap propagation of backbone literals, in order to avoid frequent execute of SAT solver. A new heuristic is used to determine the selection order of propagated backbone literals.

A searching space contains related clauses which appear in  explored justifications. It expands through the discovery of new justifications. Moreover, a deductive technique is used to dynamically expand critical clauses, which replaces model rotation.

Satisfiability determination runs through the entire seed-shrink enumeration process and takes most of the cost. We focus on the linear time solver with positive resolution which is suitable for EL+ encoding. B

available form https://github.com/MilenaLiao/Justifications-Enumeration

Ontology:
This file contains the ontology used in the experiment, and they are all EL+ logic ontology.

EL+encoding:
EL+ ontology encoding tool provided in [Sebastiani and Vescovi, 2009]. Mainly complete the normalization and classification process.

./el2sat_all -i=ontology.cel -o=ontology.o -p=ontology.ass -a=ontology.ax -v=ontology.v -z=ontology.subs"

./el2sat_all_mina -a=ontology.ass -q=query.txt -i=gene.o -z -c

Src:
ReMinA is a justification enumeration tool, we merge the encoder and solver.
“COImodule” completes the COI modularization process and obtains a simplified set of axioms.
"Unidecode" completes information summary and general encoding after modularization.
"CNFdecode" completes CNF encoding.

Tests:
We select query axioms at certain intervals from each ontology, and the selection variables of the query axioms are stored in the document.

