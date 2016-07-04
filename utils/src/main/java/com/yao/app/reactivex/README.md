###Creating Observables
Operators that originate new Observables.

* Create — create an Observable from scratch by calling observer methods programmatically
* Defer — do not create the Observable until the observer subscribes, and create a fresh Observable for each observer
* Empty/Never/Throw — create Observables that have very precise and limited behavior
* From — convert some other object or data structure into an Observable
* Interval — create an Observable that emits a sequence of integers spaced by a particular time interval
* Just — convert an object or a set of objects into an Observable that emits that or those objects
* Range — create an Observable that emits a range of sequential integers
* Repeat — create an Observable that emits a particular item or sequence of items repeatedly
* Start — create an Observable that emits the return value of a function
* Timer — create an Observable that emits a single item after a given delay
