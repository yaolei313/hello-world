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

###Transforming Observables
Operators that transform items that are emitted by an Observable.

* Buffer — periodically gather items from an Observable into bundles and emit these bundles rather than emitting the items one at a time
* FlatMap — transform the items emitted by an Observable into Observables, then flatten the emissions from those into a single Observable
* GroupBy — divide an Observable into a set of Observables that each emit a different group of items from the original Observable, organized by key
* Map — transform the items emitted by an Observable by applying a function to each item
* Scan — apply a function to each item emitted by an Observable, sequentially, and emit each successive value
* Window — periodically subdivide items from an Observable into Observable windows and emit these windows rather than emitting the items one at a time
