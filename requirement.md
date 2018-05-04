# 1. write node text with hierarchy in text area #
user write name of a node. name doesn't include tab.

__hierarchy__ >> 
user can build hierarchy of nodes using only tab.
user can only have one root node.
user is allowed to build child node only if there is a parent node.
user can have empty node.

# 2. press adapt Button in text editor pane #
user can draw new mind map into mind map pane, when user press adapt button in text editor pane.
if there are some errors in text area, then program throws an error message box.
after user got an error message from adapt button, program draws boxes next to every line of the text and fill red inside boxes which is next to the problem lines so user can recognize and fix it.

# 3. select nodes in mind map pane #
user can select a node with left click of the mouse. we call this state as __selected__ state of the node.
During the selected state, user can see special dots around the node.
mouse will change its shape properly according to its location of the special dots.
user can change size of the selected node by dragging the special dot around it.
During changing the size of the node, user can see extended line as a dotted line form.
user can change the position of the selected node by dragging the body of it not special dot.
During changing the position of the node, user may see dotted line dynamically, according to its new position with other nodes.
user can delete a selected node pressing delete button. 
if user delete a parent node, then its children will delete as well.
if user move mouse over the node, its boundary will change for user to recognize it. we call this state as __pointed__ state of the node.

# 4. adjust attributes in attribute pane #
user can change some attributes of the node in attribute pane.
user can change position : __X__, __Y__ , size : __W__, __H__ and __COLOR__ of the node.
user can not change the __X__, __Y__ and __W__, __H__ values for nagative or too big values to draw.
user can change __COLOR__ using either color picker or text field.

# 5. press change Button in attribute pane #
user can adapt all modifications to mindmap pane pressing __change__ button after modification in attribute pane.
if user modify attribute values in undesirable way, 
 change doesn't work and program throws a message. 
 after user got an error message from change button, program changes labels, which cause errors, to a red label so user can recognize and fix it.
 
# 6. save file #
user can save mind map data as __json__ format.



