# 1. Write node text with hierarchy in text area #
User can write name of a node. 
Name can't include tab.

__Hierarchy__ >> 
User can build hierarchy of nodes using only tab.
User can only have one root node.
User is allowed to build child node only if there is a parent node.
User can have empty node.

# 2. Press adapt Button in text editor pane #
User can draw new mind map into mind map pane when user press __adapt__ button in text editor pane.
If there are some errors in text area, program throws an error message box.
After user got an error message from __adapt__ button, program draws boxes next to every line of the text and fill red inside boxes which are next to the problem lines. So user can recognize and fix it.

# 3. Select nodes in mind map pane #
User can select a node with left click of the mouse. We call this state as __selected__ state of the node.
During the __selected__ state, user can see special dots around the node.
Mouse will change its shape properly according to its location of the special dots.
User can change size of the __selected__ node by dragging the special dot around it.
During changing the size of the node, user can see extended line as a dotted line form.
User can change the position of the __selected__ node by dragging the body of it not special dot.
During changing the position of the node, user may see dotted line dynamically, according to its new position with other nodes.
User can delete a __selected__ node pressing delete button. 
If user delete a parent node, then its children will delete as well.
If user move mouse over the node, its boundary will change for user to recognize it. we call this state as __pointed__ state of the node.

# 4. Adjust attributes in attribute pane #
User can change some attributes of the node in attribute pane.
User can change position : __X__, __Y__ , size : __W__, __H__ and __COLOR__ of the node.
User can not change the __X__, __Y__ and __W__, __H__ values for nagative or too big values to draw.
User can change __COLOR__ using either color picker or text field.

# 5. Press change Button in attribute pane #
User can adapt all modifications to mindmap pane pressing __change__ button after modification in attribute pane.
If user modify attribute values in undesirable way, change doesn't work and program throws a message. 
After user got an error message from __change__ button, program changes labels which cause errors to a red label, so user can recognize and fix it.
 
# 6. Save file #
User can save mind map data as __json__ format.
User can save mind map data no matter what errors there are.

# 7. Load file #
User can load mind map data which is chosen by finder.
The loaded data will be displayed exactly same as before.
If chosen data has errors, program will throw the error messages.

# 8. Select Menu Bar #
Menu bar has three components __File__, __Edit__, __View__

__File__ >> New, Open, Save, Save As.., Export To..
__Edit__ >> Undo, Redo, Delete
__View__ >> Zoom In, Zoom Out, Fit Map, Hide Editor Pane, Hide Attribute Pane





