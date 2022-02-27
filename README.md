# ArcComponents
simple SuperCollider UI components for monome arc. great for prototypes & tests.

for use with the [MonoM](https://github.com/catfact/monom) library. see the [SC grid studies](https://monome.org/docs/grid/studies/sc/).

## install

stick me in your SC extensions folder

## docs

```
//initialize an arc object
~arc = Arc.new("/monome", 0);
~arc.useDevice(0);

//create an object for our components
~comps = ArcComponents.new(~arc);

//create a number style component (value is a decimal with 1 representing a full rotation)
//args:
//1.  encoder
//2.  sensitivity (higher for slower rotation)
//3.  clamping (boolean, clamp to 0-1)
//4.  callback funtion
~comps.number(0, 2, true, { arg value; [0, value].postln });

//create an option style component (value is an integer from 0 to count - 1)
//args:
//1.  encoder
//2.  sensitivity (higher for slower rotation)
//3.  count (number of options)
//4.  callback funtion
~comps.option(1, 4, 5, { arg v; [1, v].postln });

//set or get value of an individual component
~comps.setAt(0, 0.5);
~comps.getAt(0);

//free the components object
~comps.free;
```
