# DrawingView
simple drawing view with ability to draw shapes and customize the view
change between Eraser/Pen Mode
```
DrawingV.setEraserMode(false);
```
Change Drawing Color 
```
DrawingV.setColor(Color.BLACK);
```
undo/redo/clear
```
DrawingV.undo();
DrawingV.redo();
DrawingV.clear();
```
Check ability To Undo/Redo
```
if(CanUndo()){
//something
};
if(CanRedo()){
//something
};
```
set/get pen size 
```
DrawingV.setPenSize(getPenSize()+1);
```
set Drawing Type 
```
DrawingV.setDrawType(DrawingView.TYPES.SQUARE);
//Types:- SQUARE/PEN/LINE/CIRCLE/TRIANGLE1/TRIANGLE2
```
# ScreenShot
<img src="images/img1.jpg"></img>
