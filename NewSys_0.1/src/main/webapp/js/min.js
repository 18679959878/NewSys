jQuery.cookie = function(key, value, options) {
	if(arguments.length > 1 && String(value) !== "[object Object]") {
		options = jQuery.extend({}, options);
		if(value === null || value === undefined) {
			options.expires = -1;
		}
		if(typeof options.expires === 'number') {
			var days = options.expires,
				t = options.expires = new Date();
			t.setDate(t.getDate() + days);
		}
		value = String(value);
		return(document.cookie = [encodeURIComponent(key), '=', options.raw ? value : encodeURIComponent(value), options.expires ? '; expires=' + options.expires.toUTCString() : '', options.path ? '; path=' + options.path : '', options.domain ? '; domain=' + options.domain : '', options.secure ? '; secure' : ''].join(''));
	}
	options = value || {};
	var result, decode = options.raw ? function(s) {
		return s;
	} : decodeURIComponent;
	return(result = new RegExp('(?:^|; )' + encodeURIComponent(key) + '=([^;]*)').exec(document.cookie)) ? decode(result[1]) : null;
};;
(function($) {
	"use strict";
	var console = window.console ? window.console : {
		log: $.noop,
		error: function(msg) {
			$.error(msg);
		}
	};
	var supportsProp = (('prop' in $.fn) && ('removeProp' in $.fn));

	function Wysiwyg() {
		this.controls = {
			bold: {
				groupIndex: 0,
				visible: true,
				tags: ["b", "strong"],
				css: {
					fontWeight: "bold"
				},
				tooltip: "Bold",
				hotkey: {
					"ctrl": 1,
					"key": 66
				}
			},
			copy: {
				groupIndex: 8,
				visible: false,
				tooltip: "Copy"
			},
			createLink: {
				groupIndex: 6,
				visible: true,
				exec: function() {
					var self = this;
					if($.wysiwyg.controls && $.wysiwyg.controls.link) {
						$.wysiwyg.controls.link.init(this);
					} else if($.wysiwyg.autoload) {
						$.wysiwyg.autoload.control("wysiwyg.link.js", function() {
							self.controls.createLink.exec.apply(self);
						});
					} else {
						console.error("$.wysiwyg.controls.link not defined. You need to include wysiwyg.link.js file");
					}
				},
				tags: ["a"],
				tooltip: "Create link"
			},
			cut: {
				groupIndex: 8,
				visible: false,
				tooltip: "Cut"
			},
			decreaseFontSize: {
				groupIndex: 9,
				visible: false,
				tags: ["small"],
				tooltip: "Decrease font size",
				exec: function() {
					this.decreaseFontSize();
				}
			},
			h1: {
				groupIndex: 7,
				visible: true,
				className: "h1",
				command: ($.browser.msie || $.browser.safari) ? "FormatBlock" : "heading",
				"arguments": ($.browser.msie || $.browser.safari) ? "<h1>" : "h1",
				tags: ["h1"],
				tooltip: "Header 1"
			},
			h2: {
				groupIndex: 7,
				visible: true,
				className: "h2",
				command: ($.browser.msie || $.browser.safari) ? "FormatBlock" : "heading",
				"arguments": ($.browser.msie || $.browser.safari) ? "<h2>" : "h2",
				tags: ["h2"],
				tooltip: "Header 2"
			},
			h3: {
				groupIndex: 7,
				visible: true,
				className: "h3",
				command: ($.browser.msie || $.browser.safari) ? "FormatBlock" : "heading",
				"arguments": ($.browser.msie || $.browser.safari) ? "<h3>" : "h3",
				tags: ["h3"],
				tooltip: "Header 3"
			},
			highlight: {
				tooltip: "Highlight",
				className: "highlight",
				groupIndex: 1,
				visible: false,
				css: {
					backgroundColor: "rgb(255, 255, 102)"
				},
				exec: function() {
					var command, node, selection, args;
					if($.browser.msie || $.browser.safari) {
						command = "backcolor";
					} else {
						command = "hilitecolor";
					}
					if($.browser.msie) {
						node = this.getInternalRange().parentElement();
					} else {
						selection = this.getInternalSelection();
						node = selection.extentNode || selection.focusNode;
						while(node.style === undefined) {
							node = node.parentNode;
							if(node.tagName && node.tagName.toLowerCase() === "body") {
								return;
							}
						}
					}
					if(node.style.backgroundColor === "rgb(255, 255, 102)" || node.style.backgroundColor === "#ffff66") {
						args = "#ffffff";
					} else {
						args = "#ffff66";
					}
					this.editorDoc.execCommand(command, false, args);
				}
			},
			html: {
				groupIndex: 10,
				visible: false,
				exec: function() {
					var elementHeight;
					if(this.options.resizeOptions && $.fn.resizable) {
						elementHeight = this.element.height();
					}
					if(this.viewHTML) {
						this.setContent(this.original.value);
						$(this.original).hide();
						this.editor.show();
						if(this.options.resizeOptions && $.fn.resizable) {
							if(elementHeight === this.element.height()) {
								this.element.height(elementHeight + this.editor.height());
							}
							this.element.resizable($.extend(true, {
								alsoResize: this.editor
							}, this.options.resizeOptions));
						}
						this.ui.toolbar.find("li").each(function() {
							var li = $(this);
							if(li.hasClass("html")) {
								li.removeClass("active");
							} else {
								li.removeClass('disabled');
							}
						});
					} else {
						this.saveContent();
						$(this.original).css({
							width: this.element.outerWidth() - 6,
							height: this.element.height() - this.ui.toolbar.height() - 6,
							resize: "none"
						}).show();
						this.editor.hide();
						if(this.options.resizeOptions && $.fn.resizable) {
							if(elementHeight === this.element.height()) {
								this.element.height(this.ui.toolbar.height());
							}
							this.element.resizable("destroy");
						}
						this.ui.toolbar.find("li").each(function() {
							var li = $(this);
							if(li.hasClass("html")) {
								li.addClass("active");
							} else {
								if(false === li.hasClass("fullscreen")) {
									li.removeClass("active").addClass('disabled');
								}
							}
						});
					}
					this.viewHTML = !(this.viewHTML);
				},
				tooltip: "View source code"
			},
			increaseFontSize: {
				groupIndex: 9,
				visible: false,
				tags: ["big"],
				tooltip: "Increase font size",
				exec: function() {
					this.increaseFontSize();
				}
			},
			indent: {
				groupIndex: 2,
				visible: true,
				tooltip: "Indent"
			},
			insertHorizontalRule: {
				groupIndex: 6,
				visible: true,
				tags: ["hr"],
				tooltip: "Insert Horizontal Rule"
			},
			insertImage: {
				groupIndex: 6,
				visible: true,
				exec: function() {
					var self = this;
					if($.wysiwyg.controls && $.wysiwyg.controls.image) {
						$.wysiwyg.controls.image.init(this);
					} else if($.wysiwyg.autoload) {
						$.wysiwyg.autoload.control("wysiwyg.image.js", function() {
							self.controls.insertImage.exec.apply(self);
						});
					} else {
						console.error("$.wysiwyg.controls.image not defined. You need to include wysiwyg.image.js file");
					}
				},
				tags: ["img"],
				tooltip: "Insert image"
			},
			insertOrderedList: {
				groupIndex: 5,
				visible: true,
				tags: ["ol"],
				tooltip: "Insert Ordered List"
			},
			insertTable: {
				groupIndex: 6,
				visible: true,
				exec: function() {
					var self = this;
					if($.wysiwyg.controls && $.wysiwyg.controls.table) {
						$.wysiwyg.controls.table(this);
					} else if($.wysiwyg.autoload) {
						$.wysiwyg.autoload.control("wysiwyg.table.js", function() {
							self.controls.insertTable.exec.apply(self);
						});
					} else {
						console.error("$.wysiwyg.controls.table not defined. You need to include wysiwyg.table.js file");
					}
				},
				tags: ["table"],
				tooltip: "Insert table"
			},
			insertUnorderedList: {
				groupIndex: 5,
				visible: true,
				tags: ["ul"],
				tooltip: "Insert Unordered List"
			},
			italic: {
				groupIndex: 0,
				visible: true,
				tags: ["i", "em"],
				css: {
					fontStyle: "italic"
				},
				tooltip: "Italic",
				hotkey: {
					"ctrl": 1,
					"key": 73
				}
			},
			justifyCenter: {
				groupIndex: 1,
				visible: true,
				tags: ["center"],
				css: {
					textAlign: "center"
				},
				tooltip: "Justify Center"
			},
			justifyFull: {
				groupIndex: 1,
				visible: true,
				css: {
					textAlign: "justify"
				},
				tooltip: "Justify Full"
			},
			justifyLeft: {
				visible: true,
				groupIndex: 1,
				css: {
					textAlign: "left"
				},
				tooltip: "Justify Left"
			},
			justifyRight: {
				groupIndex: 1,
				visible: true,
				css: {
					textAlign: "right"
				},
				tooltip: "Justify Right"
			},
			ltr: {
				groupIndex: 10,
				visible: false,
				exec: function() {
					var p = this.dom.getElement("p");
					if(!p) {
						return false;
					}
					$(p).attr("dir", "ltr");
					return true;
				},
				tooltip: "Left to Right"
			},
			outdent: {
				groupIndex: 2,
				visible: true,
				tooltip: "Outdent"
			},
			paragraph: {
				groupIndex: 7,
				visible: false,
				className: "paragraph",
				command: "FormatBlock",
				"arguments": ($.browser.msie || $.browser.safari) ? "<p>" : "p",
				tags: ["p"],
				tooltip: "Paragraph"
			},
			paste: {
				groupIndex: 8,
				visible: false,
				tooltip: "Paste"
			},
			redo: {
				groupIndex: 4,
				visible: true,
				tooltip: "Redo"
			},
			removeFormat: {
				groupIndex: 10,
				visible: true,
				exec: function() {
					this.removeFormat();
				},
				tooltip: "Remove formatting"
			},
			rtl: {
				groupIndex: 10,
				visible: false,
				exec: function() {
					var p = this.dom.getElement("p");
					if(!p) {
						return false;
					}
					$(p).attr("dir", "rtl");
					return true;
				},
				tooltip: "Right to Left"
			},
			strikeThrough: {
				groupIndex: 0,
				visible: true,
				tags: ["s", "strike"],
				css: {
					textDecoration: "line-through"
				},
				tooltip: "Strike-through"
			},
			subscript: {
				groupIndex: 3,
				visible: true,
				tags: ["sub"],
				tooltip: "Subscript"
			},
			superscript: {
				groupIndex: 3,
				visible: true,
				tags: ["sup"],
				tooltip: "Superscript"
			},
			underline: {
				groupIndex: 0,
				visible: true,
				tags: ["u"],
				css: {
					textDecoration: "underline"
				},
				tooltip: "Underline",
				hotkey: {
					"ctrl": 1,
					"key": 85
				}
			},
			undo: {
				groupIndex: 4,
				visible: true,
				tooltip: "Undo"
			},
			code: {
				visible: true,
				groupIndex: 6,
				tooltip: "Code snippet",
				exec: function() {
					var range = this.getInternalRange(),
						common = $(range.commonAncestorContainer),
						$nodeName = range.commonAncestorContainer.nodeName.toLowerCase();
					if(common.parent("code").length) {
						common.unwrap();
					} else {
						if($nodeName !== "body") {
							common.wrap("<code/>");
						}
					}
				}
			}
		};
		this.defaults = {
			html: '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"><html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></head><body style="margin: 3px;">INITIAL_CONTENT</body></html>',
			debug: false,
			controls: {},
			css: {},
			events: {},
			autoGrow: false,
			autoSave: true,
			brIE: true,
			formHeight: 270,
			formWidth: 440,
			iFrameClass: null,
			initialContent: "<p>Initial content</p>",
			maxHeight: 10000,
			maxLength: 0,
			messages: {
				nonSelection: "Select the text you wish to link"
			},
			toolbarHtml: '<ul role="menu" class="toolbar"></ul>',
			removeHeadings: false,
			replaceDivWithP: false,
			resizeOptions: false,
			rmUnusedControls: false,
			rmUnwantedBr: true,
			tableFiller: "Lorem ipsum",
			initialMinHeight: null,
			controlImage: {
				forceRelativeUrls: false
			},
			controlLink: {
				forceRelativeUrls: false
			},
			plugins: {
				autoload: false,
				i18n: false,
				rmFormat: {
					rmMsWordMarkup: false
				}
			}
		};
		this.availableControlProperties = ["arguments", "callback", "className", "command", "css", "custom", "exec", "groupIndex", "hotkey", "icon", "tags", "tooltip", "visible"];
		this.editor = null;
		this.editorDoc = null;
		this.element = null;
		this.options = {};
		this.original = null;
		this.savedRange = null;
		this.timers = [];
		this.validKeyCodes = [8, 9, 13, 16, 17, 18, 19, 20, 27, 33, 34, 35, 36, 37, 38, 39, 40, 45, 46];
		this.isDestroyed = false;
		this.dom = {
			ie: {
				parent: null
			},
			w3c: {
				parent: null
			}
		};
		this.dom.parent = this;
		this.dom.ie.parent = this.dom;
		this.dom.w3c.parent = this.dom;
		this.ui = {};
		this.ui.self = this;
		this.ui.toolbar = null;
		this.ui.initialHeight = null;
		this.dom.getAncestor = function(element, filterTagName) {
			filterTagName = filterTagName.toLowerCase();
			while(element && "body" !== element.tagName.toLowerCase()) {
				if(filterTagName === element.tagName.toLowerCase()) {
					return element;
				}
				element = element.parentNode;
			}
			return null;
		};
		this.dom.getElement = function(filterTagName) {
			var dom = this;
			if(window.getSelection) {
				return dom.w3c.getElement(filterTagName);
			} else {
				return dom.ie.getElement(filterTagName);
			}
		};
		this.dom.ie.getElement = function(filterTagName) {
			var dom = this.parent,
				selection = dom.parent.getInternalSelection(),
				range = selection.createRange(),
				element;
			if("Control" === selection.type) {
				if(1 === range.length) {
					element = range.item(0);
				} else {
					return null;
				}
			} else {
				element = range.parentElement();
			}
			return dom.getAncestor(element, filterTagName);
		};
		this.dom.w3c.getElement = function(filterTagName) {
			var dom = this.parent,
				range = dom.parent.getInternalRange(),
				element;
			if(!range) {
				return null;
			}
			element = range.commonAncestorContainer;
			if(3 === element.nodeType) {
				element = element.parentNode;
			}
			if(element === range.startContainer) {
				element = element.childNodes[range.startOffset];
			}
			return dom.getAncestor(element, filterTagName);
		};
		this.ui.addHoverClass = function() {
			$(this).addClass("wysiwyg-button-hover");
		};
		this.ui.appendControls = function() {
			var ui = this,
				self = this.self,
				controls = self.parseControls(),
				hasVisibleControls = true,
				groups = [],
				controlsByGroup = {},
				i, currentGroupIndex, iterateGroup = function(controlName, control) {
					if(control.groupIndex && currentGroupIndex !== control.groupIndex) {
						currentGroupIndex = control.groupIndex;
						hasVisibleControls = false;
					}
					if(!control.visible) {
						return;
					}
					if(!hasVisibleControls) {
						ui.appendItemSeparator();
						hasVisibleControls = true;
					}
					if(control.custom) {
						ui.appendItemCustom(controlName, control);
					} else {
						ui.appendItem(controlName, control);
					}
				};
			$.each(controls, function(name, c) {
				var index = "empty";
				if(undefined !== c.groupIndex) {
					if("" === c.groupIndex) {
						index = "empty";
					} else {
						index = c.groupIndex;
					}
				}
				if(undefined === controlsByGroup[index]) {
					groups.push(index);
					controlsByGroup[index] = {};
				}
				controlsByGroup[index][name] = c;
			});
			groups.sort(function(a, b) {
				if("number" === typeof(a) && typeof(a) === typeof(b)) {
					return(a - b);
				} else {
					a = a.toString();
					b = b.toString();
					if(a > b) {
						return 1;
					}
					if(a === b) {
						return 0;
					}
					return -1;
				}
			});
			if(0 < groups.length) {
				currentGroupIndex = groups[0];
			}
			for(i = 0; i < groups.length; i += 1) {
				$.each(controlsByGroup[groups[i]], iterateGroup);
			}
		};
		this.ui.appendItem = function(name, control) {
			var self = this.self,
				className = control.className || control.command || name || "empty",
				tooltip = control.tooltip || control.command || name || "";
			return $('<li role="menuitem" unselectable="on">' + (className) + "</li>").addClass(className).attr("title", tooltip).hover(this.addHoverClass, this.removeHoverClass).click(function() {
				if($(this).hasClass("disabled")) {
					return false;
				}
				self.triggerControl.apply(self, [name, control]);
				this.blur();
				self.ui.returnRange();
				self.ui.focus();
				return true;
			}).appendTo(self.ui.toolbar);
		};
		this.ui.appendItemCustom = function(name, control) {
			var self = this.self,
				tooltip = control.tooltip || control.command || name || "";
			if(control.callback) {
				$(window).bind("trigger-" + name + ".wysiwyg", control.callback);
			}
			return $('<li role="menuitem" unselectable="on" style="background: url(\'' + control.icon + '\') no-repeat;"></li>').addClass("custom-command-" + name).addClass("wysiwyg-custom-command").addClass(name).attr("title", tooltip).hover(this.addHoverClass, this.removeHoverClass).click(function() {
				if($(this).hasClass("disabled")) {
					return false;
				}
				self.triggerControl.apply(self, [name, control]);
				this.blur();
				self.ui.returnRange();
				self.ui.focus();
				self.triggerControlCallback(name);
				return true;
			}).appendTo(self.ui.toolbar);
		};
		this.ui.appendItemSeparator = function() {
			var self = this.self;
			return $('<li role="separator" class="separator"></li>').appendTo(self.ui.toolbar);
		};
		this.autoSaveFunction = function() {
			this.saveContent();
		};
		this.ui.checkTargets = function(element) {
			var self = this.self;
			$.each(self.options.controls, function(name, control) {
				var className = control.className || control.command || name || "empty",
					tags, elm, css, el, checkActiveStatus = function(cssProperty, cssValue) {
						var handler;
						if("function" === typeof(cssValue)) {
							handler = cssValue;
							if(handler(el.css(cssProperty).toString().toLowerCase(), self)) {
								self.ui.toolbar.find("." + className).addClass("active");
							}
						} else {
							if(el.css(cssProperty).toString().toLowerCase() === cssValue) {
								self.ui.toolbar.find("." + className).addClass("active");
							}
						}
					};
				if("fullscreen" !== className) {
					self.ui.toolbar.find("." + className).removeClass("active");
				}
				if(control.tags || (control.options && control.options.tags)) {
					tags = control.tags || (control.options && control.options.tags);
					elm = element;
					while(elm) {
						if(elm.nodeType !== 1) {
							break;
						}
						if($.inArray(elm.tagName.toLowerCase(), tags) !== -1) {
							self.ui.toolbar.find("." + className).addClass("active");
						}
						elm = elm.parentNode;
					}
				}
				if(control.css || (control.options && control.options.css)) {
					css = control.css || (control.options && control.options.css);
					el = $(element);
					while(el) {
						if(el[0].nodeType !== 1) {
							break;
						}
						$.each(css, checkActiveStatus);
						el = el.parent();
					}
				}
			});
		};
		this.ui.designMode = function() {
			var attempts = 3,
				self = this.self,
				runner;
			runner = function(attempts) {
				if("on" === self.editorDoc.designMode) {
					if(self.timers.designMode) {
						window.clearTimeout(self.timers.designMode);
					}
					if(self.innerDocument() !== self.editorDoc) {
						self.ui.initFrame();
					}
					return;
				}
				try {
					self.editorDoc.designMode = "on";
				} catch(e) {}
				attempts -= 1;
				if(attempts > 0) {
					self.timers.designMode = window.setTimeout(function() {
						runner(attempts);
					}, 100);
				}
			};
			runner(attempts);
		};
		this.destroy = function() {
			this.isDestroyed = true;
			var i, $form = this.element.closest("form");
			for(i = 0; i < this.timers.length; i += 1) {
				window.clearTimeout(this.timers[i]);
			}
			$form.unbind(".wysiwyg");
			this.element.remove();
			$.removeData(this.original, "wysiwyg");
			$(this.original).show();
			return this;
		};
		this.getRangeText = function() {
			var r = this.getInternalRange();
			if(r.toString) {
				r = r.toString();
			} else if(r.text) {
				r = r.text;
			}
			return r;
		};
		this.execute = function(command, arg) {
			if(typeof(arg) === "undefined") {
				arg = null;
			}
			this.editorDoc.execCommand(command, false, arg);
		};
		this.extendOptions = function(options) {
			var controls = {};
			if("object" === typeof options.controls) {
				controls = options.controls;
				delete options.controls;
			}
			options = $.extend(true, {}, this.defaults, options);
			options.controls = $.extend(true, {}, controls, this.controls, controls);
			if(options.rmUnusedControls) {
				$.each(options.controls, function(controlName) {
					if(!controls[controlName]) {
						delete options.controls[controlName];
					}
				});
			}
			return options;
		};
		this.ui.focus = function() {
			var self = this.self;
			self.editor.get(0).contentWindow.focus();
			return self;
		};
		this.ui.returnRange = function() {
			var self = this.self,
				sel;
			if(self.savedRange !== null) {
				if(window.getSelection) {
					sel = window.getSelection();
					if(sel.rangeCount > 0) {
						sel.removeAllRanges();
					}
					try {
						sel.addRange(self.savedRange);
					} catch(e) {
						console.error(e);
					}
				} else if(window.document.createRange) {
					window.getSelection().addRange(self.savedRange);
				} else if(window.document.selection) {
					self.savedRange.select();
				}
				self.savedRange = null;
			}
		};
		this.increaseFontSize = function() {
			if($.browser.mozilla || $.browser.opera) {
				this.editorDoc.execCommand('increaseFontSize', false, null);
			} else if($.browser.safari) {
				var newNode = this.editorDoc.createElement('big');
				this.getInternalRange().surroundContents(newNode);
			} else {
				console.error("Internet Explorer?");
			}
		};
		this.decreaseFontSize = function() {
			if($.browser.mozilla || $.browser.opera) {
				this.editorDoc.execCommand('decreaseFontSize', false, null);
			} else if($.browser.safari) {
				var newNode = this.editorDoc.createElement('small');
				this.getInternalRange().surroundContents(newNode);
			} else {
				console.error("Internet Explorer?");
			}
		};
		this.getContent = function() {
			return this.events.filter('getContent', this.editorDoc.body.innerHTML);
		};
		this.events = {
			_events: {},
			bind: function(eventName, callback) {
				if(typeof(this._events.eventName) !== "object") {
					this._events[eventName] = [];
				}
				this._events[eventName].push(callback);
			},
			trigger: function(eventName, args) {
				if(typeof(this._events.eventName) === "object") {
					var editor = this.editor;
					$.each(this._events[eventName], function(k, v) {
						if(typeof(v) === "function") {
							v.apply(editor, args);
						}
					});
				}
			},
			filter: function(eventName, originalText) {
				if(typeof(this._events[eventName]) === "object") {
					var editor = this.editor,
						args = Array.prototype.slice.call(arguments, 1);
					$.each(this._events[eventName], function(k, v) {
						if(typeof(v) === "function") {
							originalText = v.apply(editor, args);
						}
					});
				}
				return originalText;
			}
		};
		this.getElementByAttributeValue = function(tagName, attributeName, attributeValue) {
			var i, value, elements = this.editorDoc.getElementsByTagName(tagName);
			for(i = 0; i < elements.length; i += 1) {
				value = elements[i].getAttribute(attributeName);
				if($.browser.msie) {
					value = value.substr(value.length - attributeValue.length);
				}
				if(value === attributeValue) {
					return elements[i];
				}
			}
			return false;
		};
		this.getInternalRange = function() {
			var selection = this.getInternalSelection();
			if(!selection) {
				return null;
			}
			if(selection.rangeCount && selection.rangeCount > 0) {
				return selection.getRangeAt(0);
			} else if(selection.createRange) {
				return selection.createRange();
			}
			return null;
		};
		this.getInternalSelection = function() {
			if(this.editor.get(0).contentWindow) {
				if(this.editor.get(0).contentWindow.getSelection) {
					return this.editor.get(0).contentWindow.getSelection();
				}
				if(this.editor.get(0).contentWindow.selection) {
					return this.editor.get(0).contentWindow.selection;
				}
			}
			if(this.editorDoc.getSelection) {
				return this.editorDoc.getSelection();
			}
			if(this.editorDoc.selection) {
				return this.editorDoc.selection;
			}
			return null;
		};
		this.getRange = function() {
			var selection = this.getSelection();
			if(!selection) {
				return null;
			}
			if(selection.rangeCount && selection.rangeCount > 0) {
				selection.getRangeAt(0);
			} else if(selection.createRange) {
				return selection.createRange();
			}
			return null;
		};
		this.getSelection = function() {
			return(window.getSelection) ? window.getSelection() : window.document.selection;
		};
		this.ui.grow = function() {
			var self = this.self,
				innerBody = $(self.editorDoc.body),
				innerHeight = $.browser.msie ? innerBody[0].scrollHeight : innerBody.height() + 2 + 20,
				minHeight = self.ui.initialHeight,
				height = Math.max(innerHeight, minHeight);
			height = Math.min(height, self.options.maxHeight);
			self.editor.attr("scrolling", height < self.options.maxHeight ? "no" : "auto");
			innerBody.css("overflow", height < self.options.maxHeight ? "hidden" : "");
			self.editor.get(0).height = height;
			return self;
		};
		this.init = function(element, options) {
			var self = this,
				$form = $(element).closest("form"),
				newX = element.width || element.clientWidth || 0,
				newY = element.height || element.clientHeight || 0;
			this.options = this.extendOptions(options);
			this.original = element;
			this.ui.toolbar = $(this.options.toolbarHtml);
			if($.browser.msie && parseInt($.browser.version, 10) < 8) {
				this.options.autoGrow = false;
			}
			if(newX === 0 && element.cols) {
				newX = (element.cols * 8) + 21;
			}
			if(newY === 0 && element.rows) {
				newY = (element.rows * 16) + 16;
			}
			this.editor = $(window.location.protocol === "https:" ? '<iframe src="javascript:false;"></iframe>' : "<iframe></iframe>").attr("frameborder", "0");
			if(this.options.iFrameClass) {
				this.editor.addClass(this.options.iFrameClass);
			} else {
				this.editor.css({
					minHeight: (newY - 6).toString() + "px",
					width: (newX > 50) ? (newX - 8).toString() + "px" : ""
				});
				if($.browser.msie && parseInt($.browser.version, 10) < 7) {
					this.editor.css("height", newY.toString() + "px");
				}
			}
			this.editor.attr("tabindex", $(element).attr("tabindex"));
			this.element = $("<div/>").addClass("wysiwyg");
			if(!this.options.iFrameClass) {
				this.element.css({
					width: (newX > 0) ? newX.toString() + "px" : "100%"
				});
			}
			$(element).hide().before(this.element);
			this.viewHTML = false;
			this.initialContent = $(element).val();
			this.ui.initFrame();
			if(this.options.resizeOptions && $.fn.resizable) {
				this.element.resizable($.extend(true, {
					alsoResize: this.editor
				}, this.options.resizeOptions));
			}
			if(this.options.autoSave) {
				$form.bind("submit.wysiwyg", function() {
					self.autoSaveFunction();
				});
			}
			$form.bind("reset.wysiwyg", function() {
				self.resetFunction();
			});
		};
		this.ui.initFrame = function() {
			var self = this.self,
				stylesheet, growHandler, saveHandler;
			self.ui.appendControls();
			self.element.append(self.ui.toolbar).append($("<div><!-- --></div>").css({
				clear: "both"
			})).append(self.editor);
			self.editorDoc = self.innerDocument();
			if(self.isDestroyed) {
				return null;
			}
			self.ui.designMode();
			self.editorDoc.open();
			self.editorDoc.write(self.options.html.replace(/INITIAL_CONTENT/, function() {
				return self.wrapInitialContent();
			}));
			self.editorDoc.close();
			$.wysiwyg.plugin.bind(self);
			$(self.editorDoc).trigger("initFrame.wysiwyg");
			$(self.editorDoc).bind("click.wysiwyg", function(event) {
				self.ui.checkTargets(event.target ? event.target : event.srcElement);
			});
			$(self.original).focus(function() {
				if($(this).filter(":visible")) {
					return;
				}
				self.ui.focus();
			});
			$(self.editorDoc).keydown(function(event) {
				var emptyContentRegex;
				if(event.keyCode === 8) {
					emptyContentRegex = /^<([\w]+)[^>]*>(<br\/?>)?<\/\1>$/;
					if(emptyContentRegex.test(self.getContent())) {
						event.stopPropagation();
						return false;
					}
				}
				return true;
			});
			if(!$.browser.msie) {
				$(self.editorDoc).keydown(function(event) {
					var controlName;
					if(event.ctrlKey || event.metaKey) {
						for(controlName in self.controls) {
							if(self.controls[controlName].hotkey && self.controls[controlName].hotkey.ctrl) {
								if(event.keyCode === self.controls[controlName].hotkey.key) {
									self.triggerControl.apply(self, [controlName, self.controls[controlName]]);
									return false;
								}
							}
						}
					}
					return true;
				});
			} else if(self.options.brIE) {
				$(self.editorDoc).keydown(function(event) {
					if(event.keyCode === 13) {
						var rng = self.getRange();
						rng.pasteHTML("<br/>");
						rng.collapse(false);
						rng.select();
						return false;
					}
					return true;
				});
			}
			if(self.options.plugins.rmFormat.rmMsWordMarkup) {
				$(self.editorDoc).bind("keyup.wysiwyg", function(event) {
					if(event.ctrlKey || event.metaKey) {
						if(86 === event.keyCode) {
							if($.wysiwyg.rmFormat) {
								if("object" === typeof(self.options.plugins.rmFormat.rmMsWordMarkup)) {
									$.wysiwyg.rmFormat.run(self, {
										rules: {
											msWordMarkup: self.options.plugins.rmFormat.rmMsWordMarkup
										}
									});
								} else {
									$.wysiwyg.rmFormat.run(self, {
										rules: {
											msWordMarkup: {
												enabled: true
											}
										}
									});
								}
							}
						}
					}
				});
			}
			if(self.options.autoSave) {
				$(self.editorDoc).keydown(function() {
					self.autoSaveFunction();
				}).keyup(function() {
					self.autoSaveFunction();
				}).mousedown(function() {
					self.autoSaveFunction();
				}).bind($.support.noCloneEvent ? "input.wysiwyg" : "paste.wysiwyg", function() {
					self.autoSaveFunction();
				});
			}
			if(self.options.autoGrow) {
				if(self.options.initialMinHeight !== null) {
					self.ui.initialHeight = self.options.initialMinHeight;
				} else {
					self.ui.initialHeight = $(self.editorDoc).height();
				}
				$(self.editorDoc.body).css("border", "1px solid white");
				growHandler = function() {
					self.ui.grow();
				};
				$(self.editorDoc).keyup(growHandler);
				$(self.editorDoc).bind("editorRefresh.wysiwyg", growHandler);
				self.ui.grow();
			}
			if(self.options.css) {
				if(String === self.options.css.constructor) {
					if($.browser.msie) {
						stylesheet = self.editorDoc.createStyleSheet(self.options.css);
						$(stylesheet).attr({
							"media": "all"
						});
					} else {
						stylesheet = $("<link/>").attr({
							"href": self.options.css,
							"media": "all",
							"rel": "stylesheet",
							"type": "text/css"
						});
						$(self.editorDoc).find("head").append(stylesheet);
					}
				} else {
					self.timers.initFrame_Css = window.setTimeout(function() {
						$(self.editorDoc.body).css(self.options.css);
					}, 0);
				}
			}
			if(self.initialContent.length === 0) {
				if("function" === typeof(self.options.initialContent)) {
					self.setContent(self.options.initialContent());
				} else {
					self.setContent(self.options.initialContent);
				}
			}
			if(self.options.maxLength > 0) {
				$(self.editorDoc).keydown(function(event) {
					if($(self.editorDoc).text().length >= self.options.maxLength && $.inArray(event.which, self.validKeyCodes) === -1) {
						event.preventDefault();
					}
				});
			}
			$.each(self.options.events, function(key, handler) {
				$(self.editorDoc).bind(key + ".wysiwyg", function(event) {
					handler.apply(self.editorDoc, [event, self]);
				});
			});
			if($.browser.msie) {
				$(self.editorDoc).bind("beforedeactivate.wysiwyg", function() {
					self.savedRange = self.getInternalRange();
				});
			} else {
				$(self.editorDoc).bind("blur.wysiwyg", function() {
					self.savedRange = self.getInternalRange();
				});
			}
			$(self.editorDoc.body).addClass("wysiwyg");
			if(self.options.events && self.options.events.save) {
				saveHandler = self.options.events.save;
				$(self.editorDoc).bind("keyup.wysiwyg", saveHandler);
				$(self.editorDoc).bind("change.wysiwyg", saveHandler);
				if($.support.noCloneEvent) {
					$(self.editorDoc).bind("input.wysiwyg", saveHandler);
				} else {
					$(self.editorDoc).bind("paste.wysiwyg", saveHandler);
					$(self.editorDoc).bind("cut.wysiwyg", saveHandler);
				}
			}
			if(self.options.xhtml5 && self.options.unicode) {
				var replacements = {
					ne: 8800,
					le: 8804,
					para: 182,
					xi: 958,
					darr: 8595,
					nu: 957,
					oacute: 243,
					Uacute: 218,
					omega: 969,
					prime: 8242,
					pound: 163,
					igrave: 236,
					thorn: 254,
					forall: 8704,
					emsp: 8195,
					lowast: 8727,
					brvbar: 166,
					alefsym: 8501,
					nbsp: 160,
					delta: 948,
					clubs: 9827,
					lArr: 8656,
					Omega: 937,
					Auml: 196,
					cedil: 184,
					and: 8743,
					plusmn: 177,
					ge: 8805,
					raquo: 187,
					uml: 168,
					equiv: 8801,
					laquo: 171,
					rdquo: 8221,
					Epsilon: 917,
					divide: 247,
					fnof: 402,
					chi: 967,
					Dagger: 8225,
					iacute: 237,
					rceil: 8969,
					sigma: 963,
					Oslash: 216,
					acute: 180,
					frac34: 190,
					lrm: 8206,
					upsih: 978,
					Scaron: 352,
					part: 8706,
					exist: 8707,
					nabla: 8711,
					image: 8465,
					prop: 8733,
					zwj: 8205,
					omicron: 959,
					aacute: 225,
					Yuml: 376,
					Yacute: 221,
					weierp: 8472,
					rsquo: 8217,
					otimes: 8855,
					kappa: 954,
					thetasym: 977,
					harr: 8596,
					Ouml: 214,
					Iota: 921,
					ograve: 242,
					sdot: 8901,
					copy: 169,
					oplus: 8853,
					acirc: 226,
					sup: 8835,
					zeta: 950,
					Iacute: 205,
					Oacute: 211,
					crarr: 8629,
					Nu: 925,
					bdquo: 8222,
					lsquo: 8216,
					apos: 39,
					Beta: 914,
					eacute: 233,
					egrave: 232,
					lceil: 8968,
					Kappa: 922,
					piv: 982,
					Ccedil: 199,
					ldquo: 8220,
					Xi: 926,
					cent: 162,
					uarr: 8593,
					hellip: 8230,
					Aacute: 193,
					ensp: 8194,
					sect: 167,
					Ugrave: 217,
					aelig: 230,
					ordf: 170,
					curren: 164,
					sbquo: 8218,
					macr: 175,
					Phi: 934,
					Eta: 919,
					rho: 961,
					Omicron: 927,
					sup2: 178,
					euro: 8364,
					aring: 229,
					Theta: 920,
					mdash: 8212,
					uuml: 252,
					otilde: 245,
					eta: 951,
					uacute: 250,
					rArr: 8658,
					nsub: 8836,
					agrave: 224,
					notin: 8713,
					ndash: 8211,
					Psi: 936,
					Ocirc: 212,
					sube: 8838,
					szlig: 223,
					micro: 181,
					not: 172,
					sup1: 185,
					middot: 183,
					iota: 953,
					ecirc: 234,
					lsaquo: 8249,
					thinsp: 8201,
					sum: 8721,
					ntilde: 241,
					scaron: 353,
					cap: 8745,
					atilde: 227,
					lang: 10216,
					__replacement: 65533,
					isin: 8712,
					gamma: 947,
					Euml: 203,
					ang: 8736,
					upsilon: 965,
					Ntilde: 209,
					hearts: 9829,
					Alpha: 913,
					Tau: 932,
					spades: 9824,
					dagger: 8224,
					THORN: 222,
					"int": 8747,
					lambda: 955,
					Eacute: 201,
					Uuml: 220,
					infin: 8734,
					rlm: 8207,
					Aring: 197,
					ugrave: 249,
					Egrave: 200,
					Acirc: 194,
					rsaquo: 8250,
					ETH: 208,
					oslash: 248,
					alpha: 945,
					Ograve: 210,
					Prime: 8243,
					mu: 956,
					ni: 8715,
					real: 8476,
					bull: 8226,
					beta: 946,
					icirc: 238,
					eth: 240,
					prod: 8719,
					larr: 8592,
					ordm: 186,
					perp: 8869,
					Gamma: 915,
					reg: 174,
					ucirc: 251,
					Pi: 928,
					psi: 968,
					tilde: 732,
					asymp: 8776,
					zwnj: 8204,
					Agrave: 192,
					deg: 176,
					AElig: 198,
					times: 215,
					Delta: 916,
					sim: 8764,
					Otilde: 213,
					Mu: 924,
					uArr: 8657,
					circ: 710,
					theta: 952,
					Rho: 929,
					sup3: 179,
					diams: 9830,
					tau: 964,
					Chi: 935,
					frac14: 188,
					oelig: 339,
					shy: 173,
					or: 8744,
					dArr: 8659,
					phi: 966,
					iuml: 239,
					Lambda: 923,
					rfloor: 8971,
					iexcl: 161,
					cong: 8773,
					ccedil: 231,
					Icirc: 206,
					frac12: 189,
					loz: 9674,
					rarr: 8594,
					cup: 8746,
					radic: 8730,
					frasl: 8260,
					euml: 235,
					OElig: 338,
					hArr: 8660,
					Atilde: 195,
					Upsilon: 933,
					there4: 8756,
					ouml: 246,
					oline: 8254,
					Ecirc: 202,
					yacute: 253,
					auml: 228,
					permil: 8240,
					sigmaf: 962,
					iquest: 191,
					empty: 8709,
					pi: 960,
					Ucirc: 219,
					supe: 8839,
					Igrave: 204,
					yen: 165,
					rang: 10217,
					trade: 8482,
					lfloor: 8970,
					minus: 8722,
					Zeta: 918,
					sub: 8834,
					epsilon: 949,
					yuml: 255,
					Sigma: 931,
					Iuml: 207,
					ocirc: 244
				};
				self.events.bind("getContent", function(text) {
					return text.replace(/&(?:amp;)?(?!amp|lt|gt|quot)([a-z][a-z0-9]*);/gi, function(str, p1) {
						if(!replacements[p1]) {
							p1 = p1.toLowerCase();
							if(!replacements[p1]) {
								p1 = "__replacement";
							}
						}
						var num = replacements[p1];
						return String.fromCharCode(num);
					});
				});
			}
		};
		this.innerDocument = function() {
			var element = this.editor.get(0);
			if(element.nodeName.toLowerCase() === "iframe") {
				if(element.contentDocument) {
					return element.contentDocument;
				} else if(element.contentWindow) {
					return element.contentWindow.document;
				}
				if(this.isDestroyed) {
					return null;
				}
				console.error("Unexpected error in innerDocument");
			}
			return element;
		};
		this.insertHtml = function(szHTML) {
			var img, range;
			if(!szHTML || szHTML.length === 0) {
				return this;
			}
			if($.browser.msie) {
				this.ui.focus();
				this.editorDoc.execCommand("insertImage", false, "#jwysiwyg#");
				img = this.getElementByAttributeValue("img", "src", "#jwysiwyg#");
				if(img) {
					$(img).replaceWith(szHTML);
				}
			} else {
				if($.browser.mozilla) {
					if(1 === $(szHTML).length) {
						range = this.getInternalRange();
						range.deleteContents();
						range.insertNode($(szHTML).get(0));
					} else {
						this.editorDoc.execCommand("insertHTML", false, szHTML);
					}
				} else {
					if(!this.editorDoc.execCommand("insertHTML", false, szHTML)) {
						this.editor.focus();
						this.editorDoc.execCommand("insertHTML", false, szHTML);
					}
				}
			}
			this.saveContent();
			return this;
		};
		this.parseControls = function() {
			var self = this;
			$.each(this.options.controls, function(controlName, control) {
				$.each(control, function(propertyName) {
					if(-1 === $.inArray(propertyName, self.availableControlProperties)) {
						throw controlName + '["' + propertyName + '"]: property "' + propertyName + '" not exists in Wysiwyg.availableControlProperties';
					}
				});
			});
			if(this.options.parseControls) {
				return this.options.parseControls.call(this);
			}
			return this.options.controls;
		};
		this.removeFormat = function() {
			if($.browser.msie) {
				this.ui.focus();
			}
			if(this.options.removeHeadings) {
				this.editorDoc.execCommand("formatBlock", false, "<p>");
			}
			this.editorDoc.execCommand("removeFormat", false, null);
			this.editorDoc.execCommand("unlink", false, null);
			if($.wysiwyg.rmFormat && $.wysiwyg.rmFormat.enabled) {
				if("object" === typeof(this.options.plugins.rmFormat.rmMsWordMarkup)) {
					$.wysiwyg.rmFormat.run(this, {
						rules: {
							msWordMarkup: this.options.plugins.rmFormat.rmMsWordMarkup
						}
					});
				} else {
					$.wysiwyg.rmFormat.run(this, {
						rules: {
							msWordMarkup: {
								enabled: true
							}
						}
					});
				}
			}
			return this;
		};
		this.ui.removeHoverClass = function() {
			$(this).removeClass("wysiwyg-button-hover");
		};
		this.resetFunction = function() {
			this.setContent(this.initialContent);
		};
		this.saveContent = function() {
			if(this.original) {
				var content, newContent;
				content = this.getContent();
				if(this.options.rmUnwantedBr) {
					content = content.replace(/<br\/?>$/, "");
				}
				if(this.options.replaceDivWithP) {
					newContent = $("<div/>").addClass("temp").append(content);
					newContent.children("div").each(function() {
						var element = $(this),
							p = element.find("p"),
							i;
						if(0 === p.length) {
							p = $("<p></p>");
							if(this.attributes.length > 0) {
								for(i = 0; i < this.attributes.length; i += 1) {
									p.attr(this.attributes[i].name, element.attr(this.attributes[i].name));
								}
							}
							p.append(element.html());
							element.replaceWith(p);
						}
					});
					content = newContent.html();
				}
				$(this.original).val(content);
				if(this.options.events && this.options.events.save) {
					this.options.events.save.call(this);
				}
			}
			return this;
		};
		this.setContent = function(newContent) {
			this.editorDoc.body.innerHTML = newContent;
			this.saveContent();
			return this;
		};
		this.triggerControl = function(name, control) {
			var cmd = control.command || name,
				args = control["arguments"] || [];
			if(control.exec) {
				control.exec.apply(this);
			} else {
				this.ui.focus();
				this.ui.withoutCss();
				try {
					this.editorDoc.execCommand(cmd, false, args);
				} catch(e) {
					console.error(e);
				}
			}
			if(this.options.autoSave) {
				this.autoSaveFunction();
			}
		};
		this.triggerControlCallback = function(name) {
			$(window).trigger("trigger-" + name + ".wysiwyg", [this]);
		};
		this.ui.withoutCss = function() {
			var self = this.self;
			if($.browser.mozilla) {
				try {
					self.editorDoc.execCommand("styleWithCSS", false, false);
				} catch(e) {
					try {
						self.editorDoc.execCommand("useCSS", false, true);
					} catch(e2) {}
				}
			}
			return self;
		};
		this.wrapInitialContent = function() {
			var content = this.initialContent,
				found = content.match(/<\/?p>/gi);
			if(!found) {
				return "<p>" + content + "</p>";
			} else {}
			return content;
		};
	}
	$.wysiwyg = {
		messages: {
			noObject: "Something goes wrong, check object"
		},
		addControl: function(object, name, settings) {
			return object.each(function() {
				var oWysiwyg = $(this).data("wysiwyg"),
					customControl = {},
					toolbar;
				if(!oWysiwyg) {
					return this;
				}
				customControl[name] = $.extend(true, {
					visible: true,
					custom: true
				}, settings);
				$.extend(true, oWysiwyg.options.controls, customControl);
				toolbar = $(oWysiwyg.options.toolbarHtml);
				oWysiwyg.ui.toolbar.replaceWith(toolbar);
				oWysiwyg.ui.toolbar = toolbar;
				oWysiwyg.ui.appendControls();
			});
		},
		clear: function(object) {
			return object.each(function() {
				var oWysiwyg = $(this).data("wysiwyg");
				if(!oWysiwyg) {
					return this;
				}
				oWysiwyg.setContent("");
			});
		},
		console: console,
		destroy: function(object) {
			return object.each(function() {
				var oWysiwyg = $(this).data("wysiwyg");
				if(!oWysiwyg) {
					return this;
				}
				oWysiwyg.destroy();
			});
		},
		"document": function(object) {
			var oWysiwyg = object.data("wysiwyg");
			if(!oWysiwyg) {
				return undefined;
			}
			return $(oWysiwyg.editorDoc);
		},
		getContent: function(object) {
			var oWysiwyg = object.data("wysiwyg");
			if(!oWysiwyg) {
				return undefined;
			}
			return oWysiwyg.getContent();
		},
		init: function(object, options) {
			return object.each(function() {
				var opts = $.extend(true, {}, options),
					obj;
				if(("textarea" !== this.nodeName.toLowerCase()) || $(this).data("wysiwyg")) {
					return;
				}
				obj = new Wysiwyg();
				obj.init(this, opts);
				$.data(this, "wysiwyg", obj);
				$(obj.editorDoc).trigger("afterInit.wysiwyg");
			});
		},
		insertHtml: function(object, szHTML) {
			return object.each(function() {
				var oWysiwyg = $(this).data("wysiwyg");
				if(!oWysiwyg) {
					return this;
				}
				oWysiwyg.insertHtml(szHTML);
			});
		},
		plugin: {
			listeners: {},
			bind: function(Wysiwyg) {
				var self = this;
				$.each(this.listeners, function(action, handlers) {
					var i, plugin;
					for(i = 0; i < handlers.length; i += 1) {
						plugin = self.parseName(handlers[i]);
						$(Wysiwyg.editorDoc).bind(action + ".wysiwyg", {
							plugin: plugin
						}, function(event) {
							$.wysiwyg[event.data.plugin.name][event.data.plugin.method].apply($.wysiwyg[event.data.plugin.name], [Wysiwyg]);
						});
					}
				});
			},
			exists: function(name) {
				var plugin;
				if("string" !== typeof(name)) {
					return false;
				}
				plugin = this.parseName(name);
				if(!$.wysiwyg[plugin.name] || !$.wysiwyg[plugin.name][plugin.method]) {
					return false;
				}
				return true;
			},
			listen: function(action, handler) {
				var plugin;
				plugin = this.parseName(handler);
				if(!$.wysiwyg[plugin.name] || !$.wysiwyg[plugin.name][plugin.method]) {
					return false;
				}
				if(!this.listeners[action]) {
					this.listeners[action] = [];
				}
				this.listeners[action].push(handler);
				return true;
			},
			parseName: function(name) {
				var elements;
				if("string" !== typeof(name)) {
					return false;
				}
				elements = name.split(".");
				if(2 > elements.length) {
					return false;
				}
				return {
					name: elements[0],
					method: elements[1]
				};
			},
			register: function(data) {
				if(!data.name) {
					console.error("Plugin name missing");
				}
				$.each($.wysiwyg, function(pluginName) {
					if(pluginName === data.name) {
						console.error("Plugin with name '" + data.name + "' was already registered");
					}
				});
				$.wysiwyg[data.name] = data;
				return true;
			}
		},
		removeFormat: function(object) {
			return object.each(function() {
				var oWysiwyg = $(this).data("wysiwyg");
				if(!oWysiwyg) {
					return this;
				}
				oWysiwyg.removeFormat();
			});
		},
		save: function(object) {
			return object.each(function() {
				var oWysiwyg = $(this).data("wysiwyg");
				if(!oWysiwyg) {
					return this;
				}
				oWysiwyg.saveContent();
			});
		},
		selectAll: function(object) {
			var oWysiwyg = object.data("wysiwyg"),
				oBody, oRange, selection;
			if(!oWysiwyg) {
				return this;
			}
			oBody = oWysiwyg.editorDoc.body;
			if(window.getSelection) {
				selection = oWysiwyg.getInternalSelection();
				selection.selectAllChildren(oBody);
			} else {
				oRange = oBody.createTextRange();
				oRange.moveToElementText(oBody);
				oRange.select();
			}
		},
		setContent: function(object, newContent) {
			return object.each(function() {
				var oWysiwyg = $(this).data("wysiwyg");
				if(!oWysiwyg) {
					return this;
				}
				oWysiwyg.setContent(newContent);
			});
		},
		triggerControl: function(object, controlName) {
			return object.each(function() {
				var oWysiwyg = $(this).data("wysiwyg");
				if(!oWysiwyg) {
					return this;
				}
				if(!oWysiwyg.controls[controlName]) {
					console.error("Control '" + controlName + "' not exists");
				}
				oWysiwyg.triggerControl.apply(oWysiwyg, [controlName, oWysiwyg.controls[controlName]]);
			});
		},
		support: {
			prop: supportsProp
		},
		utils: {
			extraSafeEntities: [
				["<", ">", "'", '"', " "],
				[32]
			],
			encodeEntities: function(str) {
				var self = this,
					aStr, aRet = [];
				if(this.extraSafeEntities[1].length === 0) {
					$.each(this.extraSafeEntities[0], function(i, ch) {
						self.extraSafeEntities[1].push(ch.charCodeAt(0));
					});
				}
				aStr = str.split("");
				$.each(aStr, function(i) {
					var iC = aStr[i].charCodeAt(0);
					if($.inArray(iC, self.extraSafeEntities[1]) && (iC < 65 || iC > 127 || (iC > 90 && iC < 97))) {
						aRet.push('&#' + iC + ';');
					} else {
						aRet.push(aStr[i]);
					}
				});
				return aRet.join('');
			}
		}
	};
	$.fn.wysiwyg = function(method) {
		var args = arguments,
			plugin;
		if("undefined" !== typeof $.wysiwyg[method]) {
			args = Array.prototype.concat.call([args[0]], [this], Array.prototype.slice.call(args, 1));
			return $.wysiwyg[method].apply($.wysiwyg, Array.prototype.slice.call(args, 1));
		} else if("object" === typeof method || !method) {
			Array.prototype.unshift.call(args, this);
			return $.wysiwyg.init.apply($.wysiwyg, args);
		} else if($.wysiwyg.plugin.exists(method)) {
			plugin = $.wysiwyg.plugin.parseName(method);
			args = Array.prototype.concat.call([args[0]], [this], Array.prototype.slice.call(args, 1));
			return $.wysiwyg[plugin.name][plugin.method].apply($.wysiwyg[plugin.name], Array.prototype.slice.call(args, 1));
		} else {
			console.error("Method '" + method + "' does not exist on jQuery.wysiwyg.\nTry to include some extra controls or plugins");
		}
	};
	$.fn.getWysiwyg = function() {
		return $.data(this, "wysiwyg");
	};
})(jQuery);;
(function($) {
	$.tooltipsy = function(el, options) {
		this.options = options;
		this.$el = $(el);
		this.random = parseInt(Math.random() * 10000);
		this.ready = false;
		this.shown = false;
		this.width = 0;
		this.height = 0;
		this.delaytimer = null;
		this.$el.data("tooltipsy", this);
		this.init();
	};
	$.tooltipsy.prototype.init = function() {
		var base = this;
		base.settings = $.extend({}, base.defaults, base.options);
		base.settings.delay = parseInt(base.settings.delay);
		if(typeof base.settings.content === 'function') {
			base.readify();
		}
		base.$el.bind('mouseenter', function(e) {
			if(base.settings.delay > 0) {
				base.delaytimer = window.setTimeout(function() {
					base.enter(e);
				}, base.settings.delay);
			} else {
				base.enter(e);
			}
		}).bind('mouseleave', function(e) {
			window.clearTimeout(base.delaytimer);
			base.delaytimer = null;
			base.leave(e);
		});
	};
	$.tooltipsy.prototype.enter = function(e) {
		var base = this;
		if(base.ready === false) {
			base.readify();
		}
		if(base.shown === false) {
			if((function(o) {
					var s = 0,
						k;
					for(k in o) {
						if(o.hasOwnProperty(k)) {
							s++;
						}
					}
					return s;
				})(base.settings.css) > 0) {
				base.$tip.css(base.settings.css);
			}
			base.width = base.$tipsy.outerWidth();
			base.height = base.$tipsy.outerHeight();
		}
		if(base.settings.alignTo == 'cursor') {
			var tip_position = [e.pageX + base.settings.offset[0], e.pageY + base.settings.offset[1]];
			if(tip_position[0] + base.width > $(window).width()) {
				var tip_css = {
					top: tip_position[1] + 'px',
					right: tip_position[0] + 'px',
					left: 'auto'
				};
			} else {
				var tip_css = {
					top: tip_position[1] + 'px',
					left: tip_position[0] + 'px',
					right: 'auto'
				};
			}
		} else {
			var tip_position = [(function(pos) {
				if(base.settings.offset[0] < 0) {
					return pos.left - Math.abs(base.settings.offset[0]) - base.width;
				} else if(base.settings.offset[0] == 0) {
					return pos.left - ((base.width - base.$el.outerWidth()) / 2);
				} else {
					return pos.left + base.$el.outerWidth() + base.settings.offset[0];
				}
			})(base.offset(base.$el[0])), (function(pos) {
				if(base.settings.offset[1] < 0) {
					return pos.top - Math.abs(base.settings.offset[1]) - base.height;
				} else if(base.settings.offset[1] == 0) {
					return pos.top - ((base.height - base.$el.outerHeight()) / 2);
				} else {
					return pos.top + base.$el.outerHeight() + base.settings.offset[1];
				}
			})(base.offset(base.$el[0]))];
		}
		base.$tipsy.css({
			top: tip_position[1] + 'px',
			left: tip_position[0] + 'px'
		});
		base.settings.show(e, base.$tipsy.stop(true, true));
	};
	$.tooltipsy.prototype.leave = function(e) {
		var base = this;
		if(e.relatedTarget == base.$tip[0]) {
			base.$tip.bind('mouseleave', function(e) {
				if(e.relatedTarget == base.$el[0]) {
					return;
				}
				base.settings.hide(e, base.$tipsy.stop(true, true));
			});
			return;
		}
		base.settings.hide(e, base.$tipsy.stop(true, true));
	};
	$.tooltipsy.prototype.readify = function() {
		this.ready = true;
		this.title = this.$el.attr('title') || '';
		this.$el.attr('title', '');
		this.$tipsy = $('<div id="tooltipsy' + this.random + '">').appendTo('body').css({
			position: 'absolute',
			zIndex: '999'
		}).hide();
		this.$tip = $('<div class="' + this.settings.className + '">').appendTo(this.$tipsy);
		this.$tip.data('rootel', this.$el);
		this.$tip.html(this.settings.content != '' ? this.settings.content : this.title);
	};
	$.tooltipsy.prototype.offset = function(el) {
		var ol = ot = 0;
		if(el.offsetParent) {
			do {
				ol += el.offsetLeft;
				ot += el.offsetTop;
			} while (el = el.offsetParent);
		}
		return {
			left: ol,
			top: ot
		};
	}
	$.tooltipsy.prototype.defaults = {
		alignTo: 'element',
		offset: [0, -1],
		content: '',
		show: function(e, $el) {
			$el.fadeIn(100);
		},
		hide: function(e, $el) {
			$el.fadeOut(100);
		},
		css: {},
		className: 'tooltipsy',
		delay: 200
	};
	$.fn.tooltipsy = function(options) {
		return this.each(function() {
			new $.tooltipsy(this, options);
		});
	};
})(jQuery);;
/*
// iPhone-style Checkboxes jQuery plugin
// Copyright Thomas Reynolds, licensed GPL & MIT
*/
;
(function($, iphoneStyle) {
	$[iphoneStyle] = function(elem, options) {
		this.$elem = $(elem);
		var obj = this;
		$.each(options, function(key, value) {
			obj[key] = value;
		});
		this.wrapCheckboxWithDivs();
		this.attachEvents();
		this.disableTextSelection();
		if(this.resizeHandle) {
			this.optionallyResize('handle');
		}
		if(this.resizeContainer) {
			this.optionallyResize('container');
		}
		this.initialPosition();
	};
	$.extend($[iphoneStyle].prototype, {
		wrapCheckboxWithDivs: function() {
			this.$elem.wrap('<div class="' + this.containerClass + '" />');
			this.container = this.$elem.parent();
			this.offLabel = $('<label class="' + this.labelOffClass + '">' + '<span>' + this.uncheckedLabel + '</span>' + '</label>').appendTo(this.container);
			this.offSpan = this.offLabel.children('span');
			this.onLabel = $('<label class="' + this.labelOnClass + '">' + '<span>' + this.checkedLabel + '</span>' + '</label>').appendTo(this.container);
			this.onSpan = this.onLabel.children('span');
			this.handle = $('<div class="' + this.handleClass + '">' + '<div class="' + this.handleRightClass + '">' + '<div class="' + this.handleCenterClass + '" />' + '</div>' + '</div>').appendTo(this.container);
		},
		disableTextSelection: function() {
			if(!$.browser.msie) {
				return;
			}
			$.each([this.handle, this.offLabel, this.onLabel, this.container], function() {
				$(this).attr("unselectable", "on");
			});
		},
		optionallyResize: function(mode) {
			var onLabelWidth = this.onLabel.width(),
				offLabelWidth = this.offLabel.width();
			if(mode == 'container') {
				var newWidth = (onLabelWidth > offLabelWidth) ? onLabelWidth : offLabelWidth;
				newWidth += this.handle.width() + 15;
			} else {
				var newWidth = (onLabelWidth < offLabelWidth) ? onLabelWidth : offLabelWidth;
			}
			this[mode].css({
				width: newWidth
			});
		},
		attachEvents: function() {
			var obj = this;
			this.container.bind('mousedown touchstart', function(event) {
				event.preventDefault();
				if(obj.$elem.is(':disabled')) {
					return;
				}
				var x = event.pageX || event.originalEvent.changedTouches[0].pageX;
				$[iphoneStyle].currentlyClicking = obj.handle;
				$[iphoneStyle].dragStartPosition = x;
				$[iphoneStyle].handleLeftOffset = parseInt(obj.handle.css('left'), 10) || 0;
				$[iphoneStyle].dragStartedOn = obj.$elem;
			}).bind('iPhoneDrag', function(event, x) {
				event.preventDefault();
				if(obj.$elem.is(':disabled')) {
					return;
				}
				if(obj.$elem != $[iphoneStyle].dragStartedOn) {
					return;
				}
				var p = (x + $[iphoneStyle].handleLeftOffset - $[iphoneStyle].dragStartPosition) / obj.rightSide;
				if(p < 0) {
					p = 0;
				}
				if(p > 1) {
					p = 1;
				}
				obj.handle.css({
					left: p * obj.rightSide
				});
				obj.onLabel.css({
					width: p * obj.rightSide + 4
				});
				obj.offSpan.css({
					marginRight: -p * obj.rightSide
				});
				obj.onSpan.css({
					marginLeft: -(1 - p) * obj.rightSide
				});
			}).bind('iPhoneDragEnd', function(event, x) {
				if(obj.$elem.is(':disabled')) {
					return;
				}
				var checked;
				if($[iphoneStyle].dragging) {
					var p = (x - $[iphoneStyle].dragStartPosition) / obj.rightSide;
					checked = (p < 0) ? Math.abs(p) < 0.5 : p >= 0.5;
				} else {
					checked = !obj.$elem.attr('checked');
				}
				obj.$elem.attr('checked', checked);
				$[iphoneStyle].currentlyClicking = null;
				$[iphoneStyle].dragging = null;
				obj.$elem.change();
			});
			this.$elem.change(function() {
				if(obj.$elem.is(':disabled')) {
					obj.container.addClass(obj.disabledClass);
					return false;
				} else {
					obj.container.removeClass(obj.disabledClass);
				}
				var new_left = obj.$elem.attr('checked') ? obj.rightSide : 0;
				obj.handle.animate({
					left: new_left
				}, obj.duration);
				obj.onLabel.animate({
					width: new_left + 4
				}, obj.duration);
				obj.offSpan.animate({
					marginRight: -new_left
				}, obj.duration);
				obj.onSpan.animate({
					marginLeft: new_left - obj.rightSide
				}, obj.duration);
			});
		},
		initialPosition: function() {
			this.offLabel.css({
				width: this.container.width() - 5
			});
			var offset = ($.browser.msie && $.browser.version < 7) ? 3 : 6;
			this.rightSide = this.container.width() - this.handle.width() - offset;
			if(this.$elem.is(':checked')) {
				this.handle.css({
					left: this.rightSide
				});
				this.onLabel.css({
					width: this.rightSide + 4
				});
				this.offSpan.css({
					marginRight: -this.rightSide
				});
			} else {
				this.onLabel.css({
					width: 0
				});
				this.onSpan.css({
					marginLeft: -this.rightSide
				});
			}
			if(this.$elem.is(':disabled')) {
				this.container.addClass(this.disabledClass);
			}
		}
	});
	$.fn[iphoneStyle] = function(options) {
		var checkboxes = this.filter(':checkbox');
		if(!checkboxes.length) {
			return this;
		}
		var opt = $.extend({}, $[iphoneStyle].defaults, options);
		checkboxes.each(function() {
			$(this).data(iphoneStyle, new $[iphoneStyle](this, opt));
		});
		if(!$[iphoneStyle].initComplete) {
			$(document).bind('mousemove touchmove', function(event) {
				if(!$[iphoneStyle].currentlyClicking) {
					return;
				}
				event.preventDefault();
				var x = event.pageX || event.originalEvent.changedTouches[0].pageX;
				if(!$[iphoneStyle].dragging && (Math.abs($[iphoneStyle].dragStartPosition - x) > opt.dragThreshold)) {
					$[iphoneStyle].dragging = true;
				}
				$(event.target).trigger('iPhoneDrag', [x]);
			}).bind('mouseup touchend', function(event) {
				if(!$[iphoneStyle].currentlyClicking) {
					return;
				}
				event.preventDefault();
				var x = event.pageX || event.originalEvent.changedTouches[0].pageX;
				$($[iphoneStyle].currentlyClicking).trigger('iPhoneDragEnd', [x]);
			});
			$[iphoneStyle].initComplete = true;
		}
		return this;
	};
	$[iphoneStyle].defaults = {
		duration: 200,
		checkedLabel: 'ON',
		uncheckedLabel: 'OFF',
		resizeHandle: true,
		resizeContainer: true,
		disabledClass: 'iPhoneCheckDisabled',
		containerClass: 'iPhoneCheckContainer',
		labelOnClass: 'iPhoneCheckLabelOn',
		labelOffClass: 'iPhoneCheckLabelOff',
		handleClass: 'iPhoneCheckHandle',
		handleCenterClass: 'iPhoneCheckHandleCenter',
		handleRightClass: 'iPhoneCheckHandleRight',
		dragThreshold: 5
	};
})(jQuery, 'iphoneStyle');;
if(!document.createElement('canvas').getContext) {
	(function() {
		var m = Math;
		var mr = m.round;
		var ms = m.sin;
		var mc = m.cos;
		var abs = m.abs;
		var sqrt = m.sqrt;
		var Z = 10;
		var Z2 = Z / 2;

		function getContext() {
			return this.context_ || (this.context_ = new CanvasRenderingContext2D_(this));
		}
		var slice = Array.prototype.slice;

		function bind(f, obj, var_args) {
			var a = slice.call(arguments, 2);
			return function() {
				return f.apply(obj, a.concat(slice.call(arguments)));
			};
		}
		var G_vmlCanvasManager_ = {
			init: function(opt_doc) {
				if(/MSIE/.test(navigator.userAgent) && !window.opera) {
					var doc = opt_doc || document;
					doc.createElement('canvas');
					if(doc.readyState !== "complete") {
						doc.attachEvent('onreadystatechange', bind(this.init_, this, doc));
					} else {
						this.init_(doc);
					}
				}
			},
			init_: function(doc) {
				if(!doc.namespaces['g_vml_']) {
					doc.namespaces.add('g_vml_', 'urn:schemas-microsoft-com:vml', '#default#VML');
				}
				if(!doc.namespaces['g_o_']) {
					doc.namespaces.add('g_o_', 'urn:schemas-microsoft-com:office:office', '#default#VML');
				}
				if(!doc.styleSheets['ex_canvas_']) {
					var ss = doc.createStyleSheet();
					ss.owningElement.id = 'ex_canvas_';
					ss.cssText = 'canvas{display:inline-block;overflow:hidden;' + 'text-align:left;width:300px;height:150px}' + 'g_vml_\\:*{behavior:url(#default#VML)}' + 'g_o_\\:*{behavior:url(#default#VML)}';
				}
				var els = doc.getElementsByTagName('canvas');
				for(var i = 0; i < els.length; i++) {
					this.initElement(els[i]);
				}
			},
			initElement: function(el) {
				if(!el.getContext) {
					el.getContext = getContext;
					el.innerHTML = '';
					el.attachEvent('onpropertychange', onPropertyChange);
					el.attachEvent('onresize', onResize);
					var attrs = el.attributes;
					if(attrs.width && attrs.width.specified) {
						el.style.width = attrs.width.nodeValue + 'px';
					} else {
						el.width = el.clientWidth;
					}
					if(attrs.height && attrs.height.specified) {
						el.style.height = attrs.height.nodeValue + 'px';
					} else {
						el.height = el.clientHeight;
					}
				}
				return el;
			}
		};

		function onPropertyChange(e) {
			var el = e.srcElement;
			switch(e.propertyName) {
				case 'width':
					el.style.width = el.attributes.width.nodeValue + 'px';
					el.getContext().clearRect();
					break;
				case 'height':
					el.style.height = el.attributes.height.nodeValue + 'px';
					el.getContext().clearRect();
					break;
			}
		}

		function onResize(e) {
			var el = e.srcElement;
			if(el.firstChild) {
				el.firstChild.style.width = el.clientWidth + 'px';
				el.firstChild.style.height = el.clientHeight + 'px';
			}
		}
		G_vmlCanvasManager_.init();
		var dec2hex = [];
		for(var i = 0; i < 16; i++) {
			for(var j = 0; j < 16; j++) {
				dec2hex[i * 16 + j] = i.toString(16) + j.toString(16);
			}
		}

		function createMatrixIdentity() {
			return [
				[1, 0, 0],
				[0, 1, 0],
				[0, 0, 1]
			];
		}

		function matrixMultiply(m1, m2) {
			var result = createMatrixIdentity();
			for(var x = 0; x < 3; x++) {
				for(var y = 0; y < 3; y++) {
					var sum = 0;
					for(var z = 0; z < 3; z++) {
						sum += m1[x][z] * m2[z][y];
					}
					result[x][y] = sum;
				}
			}
			return result;
		}

		function copyState(o1, o2) {
			o2.fillStyle = o1.fillStyle;
			o2.lineCap = o1.lineCap;
			o2.lineJoin = o1.lineJoin;
			o2.lineWidth = o1.lineWidth;
			o2.miterLimit = o1.miterLimit;
			o2.shadowBlur = o1.shadowBlur;
			o2.shadowColor = o1.shadowColor;
			o2.shadowOffsetX = o1.shadowOffsetX;
			o2.shadowOffsetY = o1.shadowOffsetY;
			o2.strokeStyle = o1.strokeStyle;
			o2.globalAlpha = o1.globalAlpha;
			o2.arcScaleX_ = o1.arcScaleX_;
			o2.arcScaleY_ = o1.arcScaleY_;
			o2.lineScale_ = o1.lineScale_;
		}

		function processStyle(styleString) {
			var str, alpha = 1;
			styleString = String(styleString);
			if(styleString.substring(0, 3) == 'rgb') {
				var start = styleString.indexOf('(', 3);
				var end = styleString.indexOf(')', start + 1);
				var guts = styleString.substring(start + 1, end).split(',');
				str = '#';
				for(var i = 0; i < 3; i++) {
					str += dec2hex[Number(guts[i])];
				}
				if(guts.length == 4 && styleString.substr(3, 1) == 'a') {
					alpha = guts[3];
				}
			} else {
				str = styleString;
			}
			return {
				color: str,
				alpha: alpha
			};
		}

		function processLineCap(lineCap) {
			switch(lineCap) {
				case 'butt':
					return 'flat';
				case 'round':
					return 'round';
				case 'square':
				default:
					return 'square';
			}
		}

		function CanvasRenderingContext2D_(surfaceElement) {
			this.m_ = createMatrixIdentity();
			this.mStack_ = [];
			this.aStack_ = [];
			this.currentPath_ = [];
			this.strokeStyle = '#000';
			this.fillStyle = '#000';
			this.lineWidth = 1;
			this.lineJoin = 'miter';
			this.lineCap = 'butt';
			this.miterLimit = Z * 1;
			this.globalAlpha = 1;
			this.canvas = surfaceElement;
			var el = surfaceElement.ownerDocument.createElement('div');
			el.style.width = surfaceElement.clientWidth + 'px';
			el.style.height = surfaceElement.clientHeight + 'px';
			el.style.overflow = 'hidden';
			el.style.position = 'absolute';
			surfaceElement.appendChild(el);
			this.element_ = el;
			this.arcScaleX_ = 1;
			this.arcScaleY_ = 1;
			this.lineScale_ = 1;
		}
		var contextPrototype = CanvasRenderingContext2D_.prototype;
		contextPrototype.clearRect = function() {
			this.element_.innerHTML = '';
		};
		contextPrototype.beginPath = function() {
			this.currentPath_ = [];
		};
		contextPrototype.moveTo = function(aX, aY) {
			var p = this.getCoords_(aX, aY);
			this.currentPath_.push({
				type: 'moveTo',
				x: p.x,
				y: p.y
			});
			this.currentX_ = p.x;
			this.currentY_ = p.y;
		};
		contextPrototype.lineTo = function(aX, aY) {
			var p = this.getCoords_(aX, aY);
			this.currentPath_.push({
				type: 'lineTo',
				x: p.x,
				y: p.y
			});
			this.currentX_ = p.x;
			this.currentY_ = p.y;
		};
		contextPrototype.bezierCurveTo = function(aCP1x, aCP1y, aCP2x, aCP2y, aX, aY) {
			var p = this.getCoords_(aX, aY);
			var cp1 = this.getCoords_(aCP1x, aCP1y);
			var cp2 = this.getCoords_(aCP2x, aCP2y);
			bezierCurveTo(this, cp1, cp2, p);
		};

		function bezierCurveTo(self, cp1, cp2, p) {
			self.currentPath_.push({
				type: 'bezierCurveTo',
				cp1x: cp1.x,
				cp1y: cp1.y,
				cp2x: cp2.x,
				cp2y: cp2.y,
				x: p.x,
				y: p.y
			});
			self.currentX_ = p.x;
			self.currentY_ = p.y;
		}
		contextPrototype.quadraticCurveTo = function(aCPx, aCPy, aX, aY) {
			var cp = this.getCoords_(aCPx, aCPy);
			var p = this.getCoords_(aX, aY);
			var cp1 = {
				x: this.currentX_ + 2.0 / 3.0 * (cp.x - this.currentX_),
				y: this.currentY_ + 2.0 / 3.0 * (cp.y - this.currentY_)
			};
			var cp2 = {
				x: cp1.x + (p.x - this.currentX_) / 3.0,
				y: cp1.y + (p.y - this.currentY_) / 3.0
			};
			bezierCurveTo(this, cp1, cp2, p);
		};
		contextPrototype.arc = function(aX, aY, aRadius, aStartAngle, aEndAngle, aClockwise) {
			aRadius *= Z;
			var arcType = aClockwise ? 'at' : 'wa';
			var xStart = aX + mc(aStartAngle) * aRadius - Z2;
			var yStart = aY + ms(aStartAngle) * aRadius - Z2;
			var xEnd = aX + mc(aEndAngle) * aRadius - Z2;
			var yEnd = aY + ms(aEndAngle) * aRadius - Z2;
			if(xStart == xEnd && !aClockwise) {
				xStart += 0.125;
			}
			var p = this.getCoords_(aX, aY);
			var pStart = this.getCoords_(xStart, yStart);
			var pEnd = this.getCoords_(xEnd, yEnd);
			this.currentPath_.push({
				type: arcType,
				x: p.x,
				y: p.y,
				radius: aRadius,
				xStart: pStart.x,
				yStart: pStart.y,
				xEnd: pEnd.x,
				yEnd: pEnd.y
			});
		};
		contextPrototype.rect = function(aX, aY, aWidth, aHeight) {
			this.moveTo(aX, aY);
			this.lineTo(aX + aWidth, aY);
			this.lineTo(aX + aWidth, aY + aHeight);
			this.lineTo(aX, aY + aHeight);
			this.closePath();
		};
		contextPrototype.strokeRect = function(aX, aY, aWidth, aHeight) {
			var oldPath = this.currentPath_;
			this.beginPath();
			this.moveTo(aX, aY);
			this.lineTo(aX + aWidth, aY);
			this.lineTo(aX + aWidth, aY + aHeight);
			this.lineTo(aX, aY + aHeight);
			this.closePath();
			this.stroke();
			this.currentPath_ = oldPath;
		};
		contextPrototype.fillRect = function(aX, aY, aWidth, aHeight) {
			var oldPath = this.currentPath_;
			this.beginPath();
			this.moveTo(aX, aY);
			this.lineTo(aX + aWidth, aY);
			this.lineTo(aX + aWidth, aY + aHeight);
			this.lineTo(aX, aY + aHeight);
			this.closePath();
			this.fill();
			this.currentPath_ = oldPath;
		};
		contextPrototype.createLinearGradient = function(aX0, aY0, aX1, aY1) {
			var gradient = new CanvasGradient_('gradient');
			gradient.x0_ = aX0;
			gradient.y0_ = aY0;
			gradient.x1_ = aX1;
			gradient.y1_ = aY1;
			return gradient;
		};
		contextPrototype.createRadialGradient = function(aX0, aY0, aR0, aX1, aY1, aR1) {
			var gradient = new CanvasGradient_('gradientradial');
			gradient.x0_ = aX0;
			gradient.y0_ = aY0;
			gradient.r0_ = aR0;
			gradient.x1_ = aX1;
			gradient.y1_ = aY1;
			gradient.r1_ = aR1;
			return gradient;
		};
		contextPrototype.drawImage = function(image, var_args) {
			var dx, dy, dw, dh, sx, sy, sw, sh;
			var oldRuntimeWidth = image.runtimeStyle.width;
			var oldRuntimeHeight = image.runtimeStyle.height;
			image.runtimeStyle.width = 'auto';
			image.runtimeStyle.height = 'auto';
			var w = image.width;
			var h = image.height;
			image.runtimeStyle.width = oldRuntimeWidth;
			image.runtimeStyle.height = oldRuntimeHeight;
			if(arguments.length == 3) {
				dx = arguments[1];
				dy = arguments[2];
				sx = sy = 0;
				sw = dw = w;
				sh = dh = h;
			} else if(arguments.length == 5) {
				dx = arguments[1];
				dy = arguments[2];
				dw = arguments[3];
				dh = arguments[4];
				sx = sy = 0;
				sw = w;
				sh = h;
			} else if(arguments.length == 9) {
				sx = arguments[1];
				sy = arguments[2];
				sw = arguments[3];
				sh = arguments[4];
				dx = arguments[5];
				dy = arguments[6];
				dw = arguments[7];
				dh = arguments[8];
			} else {
				throw Error('Invalid number of arguments');
			}
			var d = this.getCoords_(dx, dy);
			var w2 = sw / 2;
			var h2 = sh / 2;
			var vmlStr = [];
			var W = 10;
			var H = 10;
			vmlStr.push(' <g_vml_:group', ' coordsize="', Z * W, ',', Z * H, '"', ' coordorigin="0,0"', ' style="width:', W, 'px;height:', H, 'px;position:absolute;');
			if(this.m_[0][0] != 1 || this.m_[0][1]) {
				var filter = [];
				filter.push('M11=', this.m_[0][0], ',', 'M12=', this.m_[1][0], ',', 'M21=', this.m_[0][1], ',', 'M22=', this.m_[1][1], ',', 'Dx=', mr(d.x / Z), ',', 'Dy=', mr(d.y / Z), '');
				var max = d;
				var c2 = this.getCoords_(dx + dw, dy);
				var c3 = this.getCoords_(dx, dy + dh);
				var c4 = this.getCoords_(dx + dw, dy + dh);
				max.x = m.max(max.x, c2.x, c3.x, c4.x);
				max.y = m.max(max.y, c2.y, c3.y, c4.y);
				vmlStr.push('padding:0 ', mr(max.x / Z), 'px ', mr(max.y / Z), 'px 0;filter:progid:DXImageTransform.Microsoft.Matrix(', filter.join(''), ", sizingmethod='clip');")
			} else {
				vmlStr.push('top:', mr(d.y / Z), 'px;left:', mr(d.x / Z), 'px;');
			}
			vmlStr.push(' ">', '<g_vml_:image src="', image.src, '"', ' style="width:', Z * dw, 'px;', ' height:', Z * dh, 'px;"', ' cropleft="', sx / w, '"', ' croptop="', sy / h, '"', ' cropright="', (w - sx - sw) / w, '"', ' cropbottom="', (h - sy - sh) / h, '"', ' />', '</g_vml_:group>');
			this.element_.insertAdjacentHTML('BeforeEnd', vmlStr.join(''));
		};
		contextPrototype.stroke = function(aFill) {
			var lineStr = [];
			var lineOpen = false;
			var a = processStyle(aFill ? this.fillStyle : this.strokeStyle);
			var color = a.color;
			var opacity = a.alpha * this.globalAlpha;
			var W = 10;
			var H = 10;
			lineStr.push('<g_vml_:shape', ' filled="', !!aFill, '"', ' style="position:absolute;width:', W, 'px;height:', H, 'px;"', ' coordorigin="0 0" coordsize="', Z * W, ' ', Z * H, '"', ' stroked="', !aFill, '"', ' path="');
			var newSeq = false;
			var min = {
				x: null,
				y: null
			};
			var max = {
				x: null,
				y: null
			};
			for(var i = 0; i < this.currentPath_.length; i++) {
				var p = this.currentPath_[i];
				var c;
				switch(p.type) {
					case 'moveTo':
						c = p;
						lineStr.push(' m ', mr(p.x), ',', mr(p.y));
						break;
					case 'lineTo':
						lineStr.push(' l ', mr(p.x), ',', mr(p.y));
						break;
					case 'close':
						lineStr.push(' x ');
						p = null;
						break;
					case 'bezierCurveTo':
						lineStr.push(' c ', mr(p.cp1x), ',', mr(p.cp1y), ',', mr(p.cp2x), ',', mr(p.cp2y), ',', mr(p.x), ',', mr(p.y));
						break;
					case 'at':
					case 'wa':
						lineStr.push(' ', p.type, ' ', mr(p.x - this.arcScaleX_ * p.radius), ',', mr(p.y - this.arcScaleY_ * p.radius), ' ', mr(p.x + this.arcScaleX_ * p.radius), ',', mr(p.y + this.arcScaleY_ * p.radius), ' ', mr(p.xStart), ',', mr(p.yStart), ' ', mr(p.xEnd), ',', mr(p.yEnd));
						break;
				}
				if(p) {
					if(min.x == null || p.x < min.x) {
						min.x = p.x;
					}
					if(max.x == null || p.x > max.x) {
						max.x = p.x;
					}
					if(min.y == null || p.y < min.y) {
						min.y = p.y;
					}
					if(max.y == null || p.y > max.y) {
						max.y = p.y;
					}
				}
			}
			lineStr.push(' ">');
			if(!aFill) {
				var lineWidth = this.lineScale_ * this.lineWidth;
				if(lineWidth < 1) {
					opacity *= lineWidth;
				}
				lineStr.push('<g_vml_:stroke', ' opacity="', opacity, '"', ' joinstyle="', this.lineJoin, '"', ' miterlimit="', this.miterLimit, '"', ' endcap="', processLineCap(this.lineCap), '"', ' weight="', lineWidth, 'px"', ' color="', color, '" />');
			} else if(typeof this.fillStyle == 'object') {
				var fillStyle = this.fillStyle;
				var angle = 0;
				var focus = {
					x: 0,
					y: 0
				};
				var shift = 0;
				var expansion = 1;
				if(fillStyle.type_ == 'gradient') {
					var x0 = fillStyle.x0_ / this.arcScaleX_;
					var y0 = fillStyle.y0_ / this.arcScaleY_;
					var x1 = fillStyle.x1_ / this.arcScaleX_;
					var y1 = fillStyle.y1_ / this.arcScaleY_;
					var p0 = this.getCoords_(x0, y0);
					var p1 = this.getCoords_(x1, y1);
					var dx = p1.x - p0.x;
					var dy = p1.y - p0.y;
					angle = Math.atan2(dx, dy) * 180 / Math.PI;
					if(angle < 0) {
						angle += 360;
					}
					if(angle < 1e-6) {
						angle = 0;
					}
				} else {
					var p0 = this.getCoords_(fillStyle.x0_, fillStyle.y0_);
					var width = max.x - min.x;
					var height = max.y - min.y;
					focus = {
						x: (p0.x - min.x) / width,
						y: (p0.y - min.y) / height
					};
					width /= this.arcScaleX_ * Z;
					height /= this.arcScaleY_ * Z;
					var dimension = m.max(width, height);
					shift = 2 * fillStyle.r0_ / dimension;
					expansion = 2 * fillStyle.r1_ / dimension - shift;
				}
				var stops = fillStyle.colors_;
				stops.sort(function(cs1, cs2) {
					return cs1.offset - cs2.offset;
				});
				var length = stops.length;
				var color1 = stops[0].color;
				var color2 = stops[length - 1].color;
				var opacity1 = stops[0].alpha * this.globalAlpha;
				var opacity2 = stops[length - 1].alpha * this.globalAlpha;
				var colors = [];
				for(var i = 0; i < length; i++) {
					var stop = stops[i];
					colors.push(stop.offset * expansion + shift + ' ' + stop.color);
				}
				lineStr.push('<g_vml_:fill type="', fillStyle.type_, '"', ' method="none" focus="100%"', ' color="', color1, '"', ' color2="', color2, '"', ' colors="', colors.join(','), '"', ' opacity="', opacity2, '"', ' g_o_:opacity2="', opacity1, '"', ' angle="', angle, '"', ' focusposition="', focus.x, ',', focus.y, '" />');
			} else {
				lineStr.push('<g_vml_:fill color="', color, '" opacity="', opacity, '" />');
			}
			lineStr.push('</g_vml_:shape>');
			this.element_.insertAdjacentHTML('beforeEnd', lineStr.join(''));
		};
		contextPrototype.fill = function() {
			this.stroke(true);
		}
		contextPrototype.closePath = function() {
			this.currentPath_.push({
				type: 'close'
			});
		};
		contextPrototype.getCoords_ = function(aX, aY) {
			var m = this.m_;
			return {
				x: Z * (aX * m[0][0] + aY * m[1][0] + m[2][0]) - Z2,
				y: Z * (aX * m[0][1] + aY * m[1][1] + m[2][1]) - Z2
			}
		};
		contextPrototype.save = function() {
			var o = {};
			copyState(this, o);
			this.aStack_.push(o);
			this.mStack_.push(this.m_);
			this.m_ = matrixMultiply(createMatrixIdentity(), this.m_);
		};
		contextPrototype.restore = function() {
			copyState(this.aStack_.pop(), this);
			this.m_ = this.mStack_.pop();
		};

		function matrixIsFinite(m) {
			for(var j = 0; j < 3; j++) {
				for(var k = 0; k < 2; k++) {
					if(!isFinite(m[j][k]) || isNaN(m[j][k])) {
						return false;
					}
				}
			}
			return true;
		}

		function setM(ctx, m, updateLineScale) {
			if(!matrixIsFinite(m)) {
				return;
			}
			ctx.m_ = m;
			if(updateLineScale) {
				var det = m[0][0] * m[1][1] - m[0][1] * m[1][0];
				ctx.lineScale_ = sqrt(abs(det));
			}
		}
		contextPrototype.translate = function(aX, aY) {
			var m1 = [
				[1, 0, 0],
				[0, 1, 0],
				[aX, aY, 1]
			];
			setM(this, matrixMultiply(m1, this.m_), false);
		};
		contextPrototype.rotate = function(aRot) {
			var c = mc(aRot);
			var s = ms(aRot);
			var m1 = [
				[c, s, 0],
				[-s, c, 0],
				[0, 0, 1]
			];
			setM(this, matrixMultiply(m1, this.m_), false);
		};
		contextPrototype.scale = function(aX, aY) {
			this.arcScaleX_ *= aX;
			this.arcScaleY_ *= aY;
			var m1 = [
				[aX, 0, 0],
				[0, aY, 0],
				[0, 0, 1]
			];
			setM(this, matrixMultiply(m1, this.m_), true);
		};
		contextPrototype.transform = function(m11, m12, m21, m22, dx, dy) {
			var m1 = [
				[m11, m12, 0],
				[m21, m22, 0],
				[dx, dy, 1]
			];
			setM(this, matrixMultiply(m1, this.m_), true);
		};
		contextPrototype.setTransform = function(m11, m12, m21, m22, dx, dy) {
			var m = [
				[m11, m12, 0],
				[m21, m22, 0],
				[dx, dy, 1]
			];
			setM(this, m, true);
		};
		contextPrototype.clip = function() {};
		contextPrototype.arcTo = function() {};
		contextPrototype.createPattern = function() {
			return new CanvasPattern_;
		};

		function CanvasGradient_(aType) {
			this.type_ = aType;
			this.x0_ = 0;
			this.y0_ = 0;
			this.r0_ = 0;
			this.x1_ = 0;
			this.y1_ = 0;
			this.r1_ = 0;
			this.colors_ = [];
		}
		CanvasGradient_.prototype.addColorStop = function(aOffset, aColor) {
			aColor = processStyle(aColor);
			this.colors_.push({
				offset: aOffset,
				color: aColor.color,
				alpha: aColor.alpha
			});
		};

		function CanvasPattern_() {}
		G_vmlCanvasManager = G_vmlCanvasManager_;
		CanvasRenderingContext2D = CanvasRenderingContext2D_;
		CanvasGradient = CanvasGradient_;
		CanvasPattern = CanvasPattern_;
	})();
};
var scr = document.getElementsByTagName('script');
var zoombox_path = scr[scr.length - 1].getAttribute("src").replace('zoombox.js', '');
(function($) {
	var options = {
		theme: 'zoombox',
		opacity: 0.8,
		duration: 800,
		animation: true,
		width: 600,
		height: 400,
		gallery: true,
		autoplay: false,
		overflow: false
	}
	var images;
	var elem;
	var isOpen = false;
	var link;
	var width;
	var height;
	var timer;
	var i = 0;
	var content;
	var type = 'multimedia';
	var position = false;
	var imageset = false;
	var state = 'closed';
	var html = '<div id="zoombox"> \
            <div class="zoombox_mask"></div>\
            <div class="zoombox_container">\
                <div class="zoombox_content"></div>\
                <div class="zoombox_title"></div>\
                <div class="zoombox_next"></div>\
                <div class="zoombox_prev"></div>\
                <div class="zoombox_close"></div>\
            </div>\
            <div class="zoombox_gallery"></div>\
        </div>';
	var filtreImg = /(\.jpg)|(\.jpeg)|(\.bmp)|(\.gif)|(\.png)/i;
	var filtreMP3 = /(\.mp3)/i;
	var filtreFLV = /(\.flv)/i;
	var filtreSWF = /(\.swf)/i;
	var filtreQuicktime = /(\.mov)|(\.mp4)/i;
	var filtreWMV = /(\.wmv)|(\.avi)/i;
	var filtreDailymotion = /(http:\/\/www.dailymotion)|(http:\/\/dailymotion)/i;
	var filtreVimeo = /(http:\/\/www.vimeo)|(http:\/\/vimeo)/i;
	var filtreYoutube = /(youtube\.)/i;
	var filtreKoreus = /(http:\/\/www\.koreus)|(http:\/\/koreus)/i;
	var galleryLoaded = 0;
	$.zoombox = function(el, options) {}
	$.zoombox.options = options;
	$.zoombox.close = function() {
		close();
	}
	$.zoombox.open = function(tmplink, opts) {
		elem = null;
		link = tmplink;
		options = $.extend({}, $.zoombox.options, opts);
		load();
	}
	$.zoombox.html = function(cont, opts) {
		content = cont;
		options = $.extend({}, $.zoombox.options, opts);
		width = options.width;
		height = options.height;
		elem = null;
		open();
	}
	$.fn.zoombox = function(opts) {
		images = new Array();
		return this.each(function() {
			if($.browser.msie && $.browser.version < 7 && !window.XMLHttpRequest) {
				return false;
			}
			var obj = this;
			var galleryRegExp = /zgallery([0-9]+)/;
			var gallery = galleryRegExp.exec($(this).attr("class"));
			var tmpimageset = false;
			if(gallery != null) {
				if(!images[gallery[1]]) {
					images[gallery[1]] = new Array();
				}
				images[gallery[1]].push($(this));
				var pos = images[gallery[1]].length - 1;
				tmpimageset = images[gallery[1]];
			}
			$(this).unbind('click').click(function() {
				options = $.extend({}, $.zoombox.options, opts);
				if(state != 'closed') return false;
				elem = $(obj);
				link = elem.attr('href');
				imageset = tmpimageset;
				position = pos;
				load();
				return false;
			});
		});
	}

	function load() {
		if(state == 'closed') isOpen = false;
		state = 'load';
		setDim();
		if(filtreImg.test(link)) {
			img = new Image();
			img.src = link;
			$("body").append('<div id="zoombox_loader"></div>');
			$("#zoombox_loader").css("marginTop", scrollY());
			timer = window.setInterval(function() {
				loadImg(img);
			}, 100);
		} else {
			setContent();
			open();
		}
	}

	function build() {
		$('body').append(html);
		$(window).keydown(function(event) {
			shortcut(event.which);
		});
		$(window).resize(function() {
			resize();
		});
		$('#zoombox .zoombox_mask').hide();
		$('#zoombox').addClass(options.theme);
		$('#zoombox .zoombox_mask,.zoombox_close').click(function() {
			close();
			return false;
		});
		if(imageset == false) {
			$('#zoombox .zoombox_next,#zoombox .zoombox_prev').remove();
		} else {
			$('#zoombox .zoombox_next').click(function() {
				next();
			});
			$('#zoombox .zoombox_prev').click(function() {
				prev();
			});
		}
	}

	function gallery() {
		var loaded = 0;
		var width = 0;
		var contentWidth = 0;
		if(options.gallery) {
			if(imageset === false) {
				$('#zoombox .zoombox_gallery').remove();
				return false;
			}
			for(var i in imageset) {
				var imgSrc = zoombox_path + 'img/video.png';
				var img = $('<img src="' + imgSrc + '" class="video gallery' + (i * 1) + '"/>');
				if(filtreImg.test(imageset[i].attr('href'))) {
					imgSrc = imageset[i].attr('href')
					img = $('<img src="' + imgSrc + '" class="gallery' + (i * 1) + '"/>');
				}
				img.data('id', i).appendTo('#zoombox .zoombox_gallery')
				img.click(function() {
					gotoSlide($(this).data('id'));
					$('#zoombox .zoombox_gallery img').removeClass('current');
					$(this).addClass('current');
				});
				if(i == position) {
					img.addClass('current');
				}
				$("<img/>").data('img', img).attr("src", imgSrc).load(function() {
					loaded++;
					var img = $(this).data('img');
					img.width(Math.round(img.height() * this.width / this.height));
					if(loaded == $('#zoombox .zoombox_gallery img').length) {
						var width = 0;
						$('#zoombox .zoombox_gallery img').each(function() {
							width += $(this).outerWidth();
							$(this).data('left', width);
						});
						var div = $('<div>').css({
							position: 'absolute',
							top: 0,
							left: 0,
							width: width
						});
						$('#zoombox .zoombox_gallery').wrapInner(div);
						contentWidth = $('#zoombox .zoombox_gallery').width();
						$('#zoombox').trigger('change');
					}
				});
			}
			$('#zoombox .zoombox_gallery').show().animate({
				bottom: 0
			}, options.duration);
		}
		$('#zoombox').bind('change', function(e, css) {
			if($('#zoombox .zoombox_gallery div').width() < $('#zoombox .zoombox_gallery').width) {
				return true;
			}
			var d = 0;
			var center = 0;
			if(css != null) {
				d = options.duration;
				center = css.width / 2;
			} else {
				center = $('#zoombox .zoombox_gallery').width() / 2;
			}
			var decal = -$('#zoombox .zoombox_gallery img.current').data('left') + $('#zoombox .zoombox_gallery img.current').width() / 2;
			var left = decal + center;
			if(left < center * 2 - $('#zoombox .zoombox_gallery div').width()) {
				left = center * 2 - $('#zoombox .zoombox_gallery div').width();
			}
			if(left > 0) {
				left = 0;
			}
			$('#zoombox .zoombox_gallery div').animate({
				left: left
			}, d);
		});
	}

	function open() {
		if(isOpen == false) build();
		else $('#zoombox .zoombox_title').empty();
		$('#zoombox .close').hide();
		$('#zoombox .zoombox_container').removeClass('multimedia').removeClass('img').addClass(type);
		if(elem != null && elem.attr('title')) {
			$('#zoombox .zoombox_title').append(elem.attr('title'));
		}
		$('#zoombox .zoombox_content').empty();
		if(type == 'img' && isOpen == false && options.animation == true) {
			$('#zoombox .zoombox_content').append(content);
		}
		if(elem != null && elem.find('img').length != 0 && isOpen == false) {
			var min = elem.find('img');
			$('#zoombox .zoombox_container').css({
				width: min.width(),
				height: min.height(),
				top: min.offset().top,
				left: min.offset().left,
				opacity: 0,
				marginTop: min.css('marginTop')
			});
		} else if(elem != null && isOpen == false) {
			$('#zoombox .zoombox_container').css({
				width: elem.width(),
				height: elem.height(),
				top: elem.offset().top,
				left: elem.offset().left
			});
		} else if(isOpen == false) {
			$('#zoombox .zoombox_container').css({
				width: 100,
				height: 100,
				top: windowH() / 2 - 50,
				left: windowW() / 2 - 50
			})
		}
		var css = {
			width: width,
			height: height,
			left: (windowW() - width) / 2,
			top: (windowH() - height) / 2,
			marginTop: scrollY(),
			opacity: 1
		};
		$('#zoombox').trigger('change', css);
		if(options.animation == true) {
			$('#zoombox .zoombox_title').hide();
			$('#zoombox .zoombox_close').hide();
			$('#zoombox .zoombox_container').animate(css, options.duration, function() {
				if(type == 'multimedia' || isOpen == true) {
					$('#zoombox .zoombox_content').append(content);
				}
				if(type == 'image' || isOpen == true) {
					$('#zoombox .zoombox_content img').css('opacity', 0).fadeTo(300, 1);
				}
				$('#zoombox .zoombox_title').fadeIn(300);
				$('#zoombox .zoombox_close').fadeIn(300);
				state = 'opened';
				if(!isOpen) {
					gallery();
				}
				isOpen = true;
			});
			$('#zoombox .zoombox_mask').fadeTo(200, options.opacity);
		} else {
			$('#zoombox .zoombox_content').append(content);
			$('#zoombox .zoombox_close').show();
			$('#zoombox .zoombox_gallery').show();
			$('#zoombox .zoombox_container').css(css);
			$('#zoombox .zoombox_mask').show();
			$('#zoombox .zoombox_mask').css('opacity', options.opacity);
			if(!isOpen) {
				gallery();
			}
			isOpen = true;
			state = 'opened';
		}
	}

	function close() {
		state = 'closing';
		window.clearInterval(timer);
		$(window).unbind('keydown');
		$(window).unbind('resize');
		if(type == 'multimedia') {
			$('#zoombox .zoombox_container').empty();
		}
		var css = {};
		if(elem != null && elem.find('img').length > 0) {
			var min = elem.find('img');
			css = {
				width: min.width(),
				height: min.height(),
				top: min.offset().top,
				left: min.offset().left,
				opacity: 0,
				marginTop: min.css('marginTop')
			};
		} else if(elem != null) {
			css = {
				width: elem.width(),
				height: elem.height(),
				top: elem.offset().top,
				left: elem.offset().left,
				marginTop: 0,
				opacity: 0
			};
		} else {
			css = {
				width: 100,
				height: 100,
				top: windowH() / 2 - 50,
				left: windowW() / 2 - 50,
				opacity: 0
			};
		}
		if(options.animation == true) {
			$('#zoombox .zoombox_mask').fadeOut(200);
			$('#zoombox .zoombox_gallery').animate({
				bottom: -$('#zoombox .zoombox_gallery').innerHeight()
			}, options.duration);
			$('#zoombox .zoombox_container').animate(css, options.duration, function() {
				$('#zoombox').remove();
				state = 'closed';
				isOpen = false;
			});
		} else {
			$('#zoombox').remove();
			state = 'closed';
			isOpen = false;
		}
	}

	function setContent() {
		if(options.overflow == false) {
			if(width * 1 + 50 > windowW()) {
				height = (windowW() - 50) * height / width;
				width = windowW() - 50;
			}
			if(height * 1 + 50 > windowH()) {
				width = (windowH() - 50) * width / height;
				height = windowH() - 50;
			}
		}
		var url = link;
		type = 'multimedia';
		if(filtreImg.test(url)) {
			type = 'img';
			content = '<img src="' + link + '" width="100%" height="100%"/>';
		} else if(filtreMP3.test(url)) {
			width = 300;
			height = 40;
			content = '<object type="application/x-shockwave-flash" data="' + MP3Player + '?son=' + url + '" width="' + width + '" height="' + height + '">';
			content += '<param name="movie" value="' + MP3Player + '?son=' + url + '" /></object>';
		} else if(filtreFLV.test(url)) {
			var autostart = 0;
			if(options.autoplay == true) {
				autostart = 1;
			}
			content = '<object type="application/x-shockwave-flash" data="' + zoombox_path + 'FLVplayer.swf" width="' + width + '" height="' + height + '">\
<param name="allowFullScreen" value="true">\
<param name="scale" value="noscale">\
<param name="wmode" value="transparent">\
<param name="flashvars" value="flv=' + url + '&autoplay=' + autostart + '">\
<embed src="' + zoombox_path + 'FLVplayer.swf" width="' + width + '" height="' + height + '" allowscriptaccess="always" allowfullscreen="true" flashvars="flv=' + url + '" wmode="transparent" />\
</object>';
		} else if(filtreSWF.test(url)) {
			content = '<object width="' + width + '" height="' + height + '"><param name="allowfullscreen" value="true" /><param name="allowscriptaccess" value="always" /><param name="movie" value="' + url + '" /><embed src="' + url + '" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="' + width + '" height="' + height + '" wmode="transparent"></embed></object>';
		} else if(filtreQuicktime.test(url)) {
			content = '<embed src="' + url + '" width="' + width + '" height="' + height + '" controller="true" cache="true" autoplay="true"/>';
		} else if(filtreWMV.test(url)) {
			content = '<embed src="' + url + '" width="' + width + '" height="' + height + '" controller="true" cache="true" autoplay="true" wmode="transparent" />';
		} else if(filtreDailymotion.test(url)) {
			var id = url.split('_');
			id = id[0].split('/');
			id = id[id.length - 1];
			if(options.autoplay == true) {
				id = id + '&autostart=1';
			}
			content = '<object width="' + width + '" height="' + height + '"><param name="movie" value="http://www.dailymotion.com/swf/' + id + '&colors=background:000000;glow:000000;foreground:FFFFFF;special:000000;&related=0"></param><param name="allowFullScreen" value="true"></param><param name="allowScriptAccess" value="always"></param><embed src="http://www.dailymotion.com/swf/' + id + '&colors=background:000000;glow:000000;foreground:FFFFFF;special:000000;&related=0" type="application/x-shockwave-flash" width="' + width + '" height="' + height + '" allowFullScreen="true" allowScriptAccess="always" wmode="transparent" ></embed></object>';
		} else if(filtreVimeo.test(url)) {
			var id = url.split('/');
			id = id[3];
			if(options.autoplay == true) {
				id = id + '&autoplay=1';
			}
			content = '<object width="' + width + '" height="' + height + '"><param name="allowfullscreen" value="true" /> <param name="allowscriptaccess" value="always" /><param name="wmode" value="transparent" /><param name="movie" value="http://www.vimeo.com/moogaloop.swf?clip_id=' + id + '&amp;server=www.vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=1&amp;color=00AAEB&amp;fullscreen=1" /> <embed src="http://www.vimeo.com/moogaloop.swf?clip_id=' + id + '&amp;server=www.vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=1&amp;color=00AAEB&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="' + width + '" height="' + height + '" wmode="transparent" ></embed></object>';
		} else if(filtreYoutube.test(url)) {
			var id = url.split('watch?v=');
			id = id[1].split('&');
			id = id[0];
			if(options.autoplay == true) {
				id = id + '&autoplay=1';
			}
			content = '<object width="' + width + '" height="' + height + '"><param name="movie" value="http://www.youtube.com/v/' + id + '&hl=fr&rel=0&color1=0xFFFFFF&color2=0xFFFFFF&hd=1"></param><embed src="http://www.youtube.com/v/' + id + '&hl=fr&rel=0&color1=0xFFFFFF&color2=0xFFFFFF&hd=1" type="application/x-shockwave-flash" width="' + width + '" height="' + height + '" wmode="transparent"></embed></object>';
		} else if(filtreKoreus.test(url)) {
			url = url.split('.html');
			url = url[0];
			content = '<object type="application/x-shockwave-flash" data="' + url + '" width="' + width + '" height="' + height + '"><param name="movie" value="' + url + '"><embed src="' + url + '" type="application/x-shockwave-flash" width="' + width + '" height="' + height + '"  wmode="transparent"></embed></object>';
		} else {
			content = '<iframe src="' + url + '" width="' + width + '" height="' + height + '" border="0"></iframe>';
		}
		return content;
	}

	function loadImg(img) {
		if(img.complete) {
			i = 0;
			window.clearInterval(timer);
			width = img.width;
			height = img.height;
			$('#zoombox_loader').remove();
			setContent();
			open();
		}
		$('#zoombox_loader').css({
			'background-position': "0px " + i + "px"
		});
		i = i - 40;
		if(i < (-440)) {
			i = 0;
		}
	}

	function gotoSlide(i) {
		if(state != 'opened') {
			return false;
		}
		position = i;
		elem = imageset[position];
		link = elem.attr('href');
		if($('#zoombox .zoombox_gallery img').length > 0) {
			$('#zoombox .zoombox_gallery img').removeClass('current');
			$('#zoombox .zoombox_gallery img:eq(' + i + ')').addClass('current');
		}
		load();
		return false;
	}

	function next() {
		i = position + 1;
		if(i > imageset.length - 1) {
			i = 0;
		}
		gotoSlide(i);
	}

	function prev() {
		i = position - 1;
		if(i < 0) {
			i = imageset.length - 1;
		}
		gotoSlide(i);
	}

	function resize() {
		$('#zoombox .zoombox_container').css({
			top: (windowH() - $('#zoombox .zoombox_container').outerHeight(true)) / 2,
			left: (windowW() - $('#zoombox .zoombox_container').outerWidth(true)) / 2
		});
	}

	function shortcut(key) {
		if(key == 37) {
			prev();
		}
		if(key == 39) {
			next();
		}
		if(key == 27) {
			close();
		}
	}

	function setDim() {
		width = options.width;
		height = options.height;
		if(elem != null) {
			var widthReg = /w([0-9]+)/;
			var w = widthReg.exec(elem.attr("class"));
			if(w != null) {
				if(w[1]) {
					width = w[1];
				}
			}
			var heightReg = /h([0-9]+)/;
			var h = heightReg.exec(elem.attr("class"));
			if(h != null) {
				if(h[1]) {
					height = h[1];
				}
			}
		}
		return false;
	}

	function windowH() {
		if(window.innerHeight) return window.innerHeight;
		else {
			return $(window).height();
		}
	}

	function windowW() {
		if(window.innerWidth) return window.innerWidth;
		else {
			return $(window).width();
		}
	}

	function scrollY() {
		scrOfY = 0;
		if(typeof(window.pageYOffset) == 'number') {
			scrOfY = window.pageYOffset;
		} else if(document.body && (document.body.scrollTop)) {
			scrOfY = document.body.scrollTop;
		} else if(document.documentElement && (document.documentElement.scrollTop)) {
			scrOfY = document.documentElement.scrollTop;
		}
		return scrOfY;
	}

	function scrollX() {
		scrOfX = 0;
		if(typeof(window.pageXOffset) == 'number') {
			scrOfX = window.pageXOffset;
		} else if(document.body && (document.body.scrollLeft)) {
			scrOfX = document.body.scrollLeft;
		} else if(document.documentElement && (document.documentElement.scrollLeft)) {
			scrOfX = document.documentElement.scrollLeft;
		}
		return scrOfX;
	}
})(jQuery);;
(function($) {
	$.fn.visualize = function(options, container) {
		return $(this).each(function() {
			var o = $.extend({
				type: 'bar',
				width: $(this).width(),
				height: $(this).height(),
				appendTitle: true,
				title: null,
				appendKey: true,
				colors: ['#be1e2d', '#666699', '#92d5ea', '#ee8310', '#8d10ee', '#5a3b16', '#26a4ed', '#f45a90', '#e9e744'],
				textColors: [],
				parseDirection: 'x',
				pieMargin: 10,
				pieLabelsAsPercent: true,
				pieLabelPos: 'inside',
				lineWeight: 4,
				lineDots: false,
				dotInnerColor: "#ffffff",
				lineMargin: (options.lineDots ? 15 : 0),
				barGroupMargin: 10,
				chartId: '',
				xLabelParser: null,
				valueParser: null,
				chartId: '',
				chartClass: '',
				barMargin: 1,
				yLabelInterval: 30,
				interaction: false
			}, options);
			o.width = parseFloat(o.width);
			o.height = parseFloat(o.height);
			if(o.type != 'line' && o.type != 'area') {
				o.lineMargin = 0;
			}
			var self = $(this);
			var tableData = {};
			var colors = o.colors;
			var textColors = o.textColors;
			var parseLabels = function(direction) {
				var labels = [];
				if(direction == 'x') {
					self.find('thead tr').each(function(i) {
						$(this).find('th').each(function(j) {
							if(!labels[j]) {
								labels[j] = [];
							}
							labels[j][i] = $(this).text()
						})
					});
				} else {
					self.find('tbody tr').each(function(i) {
						$(this).find('th').each(function(j) {
							if(!labels[i]) {
								labels[i] = [];
							}
							labels[i][j] = $(this).text()
						});
					});
				}
				return labels;
			};
			var fnParse = o.valueParser || parseFloat;
			var dataGroups = tableData.dataGroups = [];
			if(o.parseDirection == 'x') {
				self.find('tbody tr').each(function(i, tr) {
					dataGroups[i] = {};
					dataGroups[i].points = [];
					dataGroups[i].color = colors[i];
					if(textColors[i]) {
						dataGroups[i].textColor = textColors[i];
					}
					$(tr).find('td').each(function(j, td) {
						dataGroups[i].points.push({
							value: fnParse($(td).text()),
							elem: td,
							tableCords: [i, j]
						});
					});
				});
			} else {
				var cols = self.find('tbody tr:eq(0) td').size();
				for(var i = 0; i < cols; i++) {
					dataGroups[i] = {};
					dataGroups[i].points = [];
					dataGroups[i].color = colors[i];
					if(textColors[i]) {
						dataGroups[i].textColor = textColors[i];
					}
					self.find('tbody tr').each(function(j) {
						dataGroups[i].points.push({
							value: $(this).find('td').eq(i).text() * 1,
							elem: this,
							tableCords: [i, j]
						});
					});
				};
			}
			var allItems = tableData.allItems = [];
			$(dataGroups).each(function(i, row) {
				var count = 0;
				$.each(row.points, function(j, point) {
					allItems.push(point);
					count += point.value;
				});
				row.groupTotal = count;
			});
			tableData.dataSum = 0;
			tableData.topValue = 0;
			tableData.bottomValue = Infinity;
			$.each(allItems, function(i, item) {
				tableData.dataSum += fnParse(item.value);
				if(fnParse(item.value, 10) > tableData.topValue) {
					tableData.topValue = fnParse(item.value, 10);
				}
				if(item.value < tableData.bottomValue) {
					tableData.bottomValue = fnParse(item.value);
				}
			});
			var dataSum = tableData.dataSum;
			var topValue = tableData.topValue;
			var bottomValue = tableData.bottomValue;
			var xAllLabels = tableData.xAllLabels = parseLabels(o.parseDirection);
			var yAllLabels = tableData.yAllLabels = parseLabels(o.parseDirection === 'x' ? 'y' : 'x');
			var xLabels = tableData.xLabels = [];
			$.each(tableData.xAllLabels, function(i, labels) {
				tableData.xLabels.push(labels[0]);
			});
			var totalYRange = tableData.totalYRange = tableData.topValue - tableData.bottomValue;
			var zeroLocX = tableData.zeroLocX = 0;
			if($.isFunction(o.xLabelParser)) {
				var xTopValue = null;
				var xBottomValue = null;
				$.each(xLabels, function(i, label) {
					label = xLabels[i] = o.xLabelParser(label);
					if(i === 0) {
						xTopValue = label;
						xBottomValue = label;
					}
					if(label > xTopValue) {
						xTopValue = label;
					}
					if(label < xBottomValue) {
						xBottomValue = label;
					}
				});
				var totalXRange = tableData.totalXRange = xTopValue - xBottomValue;
				var xScale = tableData.xScale = (o.width - 2 * o.lineMargin) / totalXRange;
				var marginDiffX = 0;
				if(o.lineMargin) {
					var marginDiffX = -2 * xScale - o.lineMargin;
				}
				zeroLocX = tableData.zeroLocX = xBottomValue + o.lineMargin;
				tableData.xBottomValue = xBottomValue;
				tableData.xTopValue = xTopValue;
				tableData.totalXRange = totalXRange;
			}
			var yScale = tableData.yScale = (o.height - 2 * o.lineMargin) / totalYRange;
			var zeroLocY = tableData.zeroLocY = (o.height - 2 * o.lineMargin) * (tableData.topValue / tableData.totalYRange) + o.lineMargin;
			var yLabels = tableData.yLabels = [];
			var numLabels = Math.floor((o.height - 2 * o.lineMargin) / 30);
			var loopInterval = tableData.totalYRange / numLabels;
			loopInterval = Math.round(parseFloat(loopInterval) / 5) * 5;
			loopInterval = Math.max(loopInterval, 1);
			for(var j = Math.round(parseInt(tableData.bottomValue) / 5) * 5; j <= tableData.topValue + loopInterval; j += loopInterval) {
				yLabels.push(j);
			}
			if(yLabels[yLabels.length - 1] > tableData.topValue + loopInterval) {
				yLabels.pop();
			} else if(yLabels[yLabels.length - 1] <= tableData.topValue - 10) {
				yLabels.push(tableData.topValue);
			}
			$.each(dataGroups, function(i, row) {
				row.yLabels = tableData.yAllLabels[i];
				$.each(row.points, function(j, point) {
					point.zeroLocY = tableData.zeroLocY;
					point.zeroLocX = tableData.zeroLocX;
					point.xLabels = tableData.xAllLabels[j];
					point.yLabels = tableData.yAllLabels[i];
					point.color = row.color;
				});
			});
			try {
				console.log(tableData);
			} catch(e) {}
			var charts = {};
			charts.pie = {
				interactionPoints: dataGroups,
				setup: function() {
					charts.pie.draw(true);
				},
				draw: function(drawHtml) {
					var centerx = Math.round(canvas.width() / 2);
					var centery = Math.round(canvas.height() / 2);
					var radius = centery - o.pieMargin;
					var counter = 0.0;
					if(drawHtml) {
						canvasContain.addClass('visualize-pie');
						if(o.pieLabelPos == 'outside') {
							canvasContain.addClass('visualize-pie-outside');
						}
						var toRad = function(integer) {
							return(Math.PI / 180) * integer;
						};
						var labels = $('<ul class="visualize-labels"></ul>').insertAfter(canvas);
					}
					$.each(dataGroups, function(i, row) {
						var fraction = row.groupTotal / dataSum;
						if(fraction <= 0 || isNaN(fraction))
							return;
						ctx.beginPath();
						ctx.moveTo(centerx, centery);
						ctx.arc(centerx, centery, radius, counter * Math.PI * 2 - Math.PI * 0.5, (counter + fraction) * Math.PI * 2 - Math.PI * 0.5, false);
						ctx.lineTo(centerx, centery);
						ctx.closePath();
						ctx.fillStyle = dataGroups[i].color;
						ctx.fill();
						if(drawHtml) {
							var sliceMiddle = (counter + fraction / 2);
							var distance = o.pieLabelPos == 'inside' ? radius / 1.5 : radius + radius / 5;
							var labelx = Math.round(centerx + Math.sin(sliceMiddle * Math.PI * 2) * (distance));
							var labely = Math.round(centery - Math.cos(sliceMiddle * Math.PI * 2) * (distance));
							var leftRight = (labelx > centerx) ? 'right' : 'left';
							var topBottom = (labely > centery) ? 'bottom' : 'top';
							var percentage = parseFloat((fraction * 100).toFixed(2));
							row.canvasCords = [labelx, labely];
							row.zeroLocY = tableData.zeroLocY = 0;
							row.zeroLocX = tableData.zeroLocX = 0;
							row.value = row.groupTotal;
							if(percentage) {
								var labelval = (o.pieLabelsAsPercent) ? percentage + '%' : row.groupTotal;
								var labeltext = $('<span class="visualize-label">' + labelval + '</span>').css(leftRight, 0).css(topBottom, 0);
								if(labeltext)
									var label = $('<li class="visualize-label-pos"></li>').appendTo(labels).css({
										left: labelx,
										top: labely
									}).append(labeltext);
								labeltext.css('font-size', radius / 8).css('margin-' + leftRight, -labeltext.width() / 2).css('margin-' + topBottom, -labeltext.outerHeight() / 2);
								if(dataGroups[i].textColor) {
									labeltext.css('color', dataGroups[i].textColor);
								}
							}
						}
						counter += fraction;
					});
				}
			};
			(function() {
				var xInterval;
				var drawPoint = function(ctx, x, y, color, size) {
					ctx.moveTo(x, y);
					ctx.beginPath();
					ctx.arc(x, y, size / 2, 0, 2 * Math.PI, false);
					ctx.closePath();
					ctx.fillStyle = color;
					ctx.fill();
				};
				charts.line = {
					interactionPoints: allItems,
					setup: function(area) {
						if(area) {
							canvasContain.addClass('visualize-area');
						} else {
							canvasContain.addClass('visualize-line');
						}
						var xlabelsUL = $('<ul class="visualize-labels-x"></ul>').width(canvas.width()).height(canvas.height()).insertBefore(canvas);
						if(!o.customXLabels) {
							xInterval = (canvas.width() - 2 * o.lineMargin) / (xLabels.length - 1);
							$.each(xLabels, function(i) {
								var thisLi = $('<li><span>' + this + '</span></li>').prepend('<span class="line" />').css('left', o.lineMargin + xInterval * i).appendTo(xlabelsUL);
								var label = thisLi.find('span:not(.line)');
								var leftOffset = label.width() / -2;
								if(i == 0) {
									leftOffset = 0;
								} else if(i == xLabels.length - 1) {
									leftOffset = -label.width();
								}
								label.css('margin-left', leftOffset).addClass('label');
							});
						} else {
							o.customXLabels(tableData, xlabelsUL);
						}
						var liBottom = (canvas.height() - 2 * o.lineMargin) / (yLabels.length - 1);
						var ylabelsUL = $('<ul class="visualize-labels-y"></ul>').width(canvas.width()).height(canvas.height()).insertBefore(scroller);
						$.each(yLabels, function(i) {
							var value = Math.floor(this);
							var posB = (value - bottomValue) * yScale + o.lineMargin;
							if(posB >= o.height - 1 || posB < 0) {
								return;
							}
							var thisLi = $('<li><span>' + value + '</span></li>').css('bottom', posB);
							if(Math.abs(posB) < o.height - 1) {
								thisLi.prepend('<span class="line"  />');
							}
							thisLi.prependTo(ylabelsUL);
							var label = thisLi.find('span:not(.line)');
							var topOffset = label.height() / -2;
							if(!o.lineMargin) {
								if(i == 0) {
									topOffset = -label.height();
								} else if(i == yLabels.length - 1) {
									topOffset = 0;
								}
							}
							label.css('margin-top', topOffset).addClass('label');
						});
						ctx.translate(zeroLocX, zeroLocY);
						charts.line.draw(area);
					},
					draw: function(area) {
						ctx.clearRect(-zeroLocX, -zeroLocY, o.width, o.height);
						var integer;
						$.each(dataGroups, function(i, row) {
							integer = o.lineMargin;
							$.each(row.points, function(j, point) {
								if(o.xLabelParser) {
									point.canvasCords = [(xLabels[j] - zeroLocX) * xScale - xBottomValue, -(point.value * yScale)];
								} else {
									point.canvasCords = [integer, -(point.value * yScale)];
								}
								if(o.lineDots) {
									point.dotSize = o.dotSize || o.lineWeight * Math.PI;
									point.dotInnerSize = o.dotInnerSize || o.lineWeight * Math.PI / 2;
									if(o.lineDots == 'double') {
										point.innerColor = o.dotInnerColor;
									}
								}
								integer += xInterval;
							});
						});
						self.trigger('vizualizeBeforeDraw', {
							options: o,
							table: self,
							canvasContain: canvasContain,
							tableData: tableData
						});
						$.each(dataGroups, function(h) {
							ctx.beginPath();
							ctx.lineWidth = o.lineWeight;
							ctx.lineJoin = 'round';
							$.each(this.points, function(g) {
								var loc = this.canvasCords;
								if(g == 0) {
									ctx.moveTo(loc[0], loc[1]);
								}
								ctx.lineTo(loc[0], loc[1]);
							});
							ctx.strokeStyle = this.color;
							ctx.stroke();
							if(area) {
								var integer = this.points[this.points.length - 1].canvasCords[0];
								if(isFinite(integer))
									ctx.lineTo(integer, 0);
								ctx.lineTo(o.lineMargin, 0);
								ctx.closePath();
								ctx.fillStyle = this.color;
								ctx.globalAlpha = .3;
								ctx.fill();
								ctx.globalAlpha = 1.0;
							} else {
								ctx.closePath();
							}
						});
						if(o.lineDots) {
							$.each(dataGroups, function(h) {
								$.each(this.points, function(g) {
									drawPoint(ctx, this.canvasCords[0], this.canvasCords[1], this.color, this.dotSize);
									if(o.lineDots === 'double') {
										drawPoint(ctx, this.canvasCords[0], this.canvasCords[1], this.innerColor, this.dotInnerSize);
									}
								});
							});
						}
					}
				};
			})();
			charts.area = {
				setup: function() {
					charts.line.setup(true);
				},
				draw: charts.line.draw
			};
			(function() {
				var horizontal, bottomLabels;
				charts.bar = {
					setup: function() {
						horizontal = (o.barDirection == 'horizontal');
						canvasContain.addClass('visualize-bar');
						bottomLabels = horizontal ? yLabels : xLabels;
						var xInterval = canvas.width() / (bottomLabels.length - (horizontal ? 1 : 0));
						var xlabelsUL = $('<ul class="visualize-labels-x"></ul>').width(canvas.width()).height(canvas.height()).insertBefore(canvas);
						$.each(bottomLabels, function(i) {
							var thisLi = $('<li><span class="label">' + this + '</span></li>').prepend('<span class="line" />').css('left', xInterval * i).width(xInterval).appendTo(xlabelsUL);
							if(horizontal) {
								var label = thisLi.find('span.label');
								label.css("margin-left", -label.width() / 2);
							}
						});
						var leftLabels = horizontal ? xLabels : yLabels;
						var liBottom = canvas.height() / (leftLabels.length - (horizontal ? 0 : 1));
						var ylabelsUL = $('<ul class="visualize-labels-y"></ul>').width(canvas.width()).height(canvas.height()).insertBefore(canvas);
						$.each(leftLabels, function(i) {
							var thisLi = $('<li><span>' + this + '</span></li>').prependTo(ylabelsUL);
							var label = thisLi.find('span:not(.line)').addClass('label');
							if(horizontal) {
								label.css({
									'min-height': liBottom,
									'max-height': liBottom + 1,
									'vertical-align': 'middle'
								});
								thisLi.css({
									'top': liBottom * i,
									'min-height': liBottom
								});
								var r = label[0].getClientRects()[0];
								if(r.bottom - r.top == liBottom) {
									label.css('line-height', parseInt(liBottom) + 'px');
								} else {
									label.css("overflow", "hidden");
								}
							} else {
								thisLi.css('bottom', liBottom * i).prepend('<span class="line" />');
								label.css('margin-top', -label.height() / 2)
							}
						});
						charts.bar.draw();
					},
					draw: function() {
						if(horizontal) {
							ctx.rotate(Math.PI / 2);
						} else {
							ctx.translate(0, zeroLocY);
						}
						if(totalYRange <= 0)
							return;
						var yScale = (horizontal ? canvas.width() : canvas.height()) / totalYRange;
						var barWidth = horizontal ? (canvas.height() / xLabels.length) : (canvas.width() / (bottomLabels.length));
						var linewidth = (barWidth - o.barGroupMargin * 2) / dataGroups.length;
						for(var h = 0; h < dataGroups.length; h++) {
							ctx.beginPath();
							var strokeWidth = linewidth - (o.barMargin * 2);
							ctx.lineWidth = strokeWidth;
							var points = dataGroups[h].points;
							var integer = 0;
							for(var i = 0; i < points.length; i++) {
								if(points[i].value != 0) {
									var xVal = (integer - o.barGroupMargin) + (h * linewidth) + linewidth / 2;
									xVal += o.barGroupMargin * 2;
									ctx.moveTo(xVal, 0);
									ctx.lineTo(xVal, Math.round(-points[i].value * yScale));
								}
								integer += barWidth;
							}
							ctx.strokeStyle = dataGroups[h].color;
							ctx.stroke();
							ctx.closePath();
						}
					}
				};
			})();
			var canvasNode = document.createElement("canvas");
			var canvas = $(canvasNode).attr({
				'height': o.height,
				'width': o.width
			});
			var title = o.title || self.find('caption').text();
			var canvasContain = (container || $('<div ' + (o.chartId ? 'id="' + o.chartId + '" ' : '') + 'class="visualize ' + o.chartClass + '" role="img" aria-label="Chart representing data from the table: ' + title + '" />')).height(o.height).width(o.width);
			var scroller = $('<div class="visualize-scroller"></div>').appendTo(canvasContain).append(canvas);
			if(o.appendTitle || o.appendKey) {
				var infoContain = $('<div class="visualize-info"></div>').appendTo(canvasContain);
			}
			if(o.appendTitle) {
				$('<div class="visualize-title">' + title + '</div>').appendTo(infoContain);
			}
			if(o.appendKey) {
				var newKey = $('<ul class="visualize-key"></ul>');
				$.each(yAllLabels, function(i, label) {
					$('<li><span class="visualize-key-color" style="background: ' + dataGroups[i].color + '"></span><span class="visualize-key-label">' + label + '</span></li>').appendTo(newKey);
				});
				newKey.appendTo(infoContain);
			};
			if(o.interaction) {
				var tracker = $('<div class="visualize-interaction-tracker"/>').css({
					'height': o.height + 'px',
					'width': o.width + 'px',
					'position': 'relative',
					'z-index': 200
				}).insertAfter(canvas);
				var triggerInteraction = function(overOut, data) {
					var data = $.extend({
						canvasContain: canvasContain,
						tableData: tableData
					}, data);
					self.trigger('vizualize' + overOut, data);
				};
				var over = false,
					last = false,
					started = false;
				tracker.mousemove(function(e) {
					var x, y, x1, y1, data, dist, i, current, selector, zLabel, elem, color, minDist, found, ev = e.originalEvent;
					x = ev.layerX || ev.offsetX || 0;
					y = ev.layerY || ev.offsetY || 0;
					found = false;
					minDist = started ? 30000 : (o.type == 'pie' ? (Math.round(canvas.height() / 2) - o.pieMargin) / 3 : o.lineWeight * 4);
					$.each(charts[o.type].interactionPoints, function(i, current) {
						x1 = current.canvasCords[0] + zeroLocX;
						y1 = current.canvasCords[1] + (o.type == "pie" ? 0 : zeroLocY);
						dist = Math.sqrt((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y));
						if(dist < minDist) {
							found = current;
							minDist = dist;
						}
					});
					if(o.multiHover && found) {
						x = found.canvasCords[0] + zeroLocX;
						y = found.canvasCords[1] + (o.type == "pie" ? 0 : zeroLocY);
						found = [found];
						$.each(charts[o.type].interactionPoints, function(i, current) {
							if(current == found[0]) {
								return;
							}
							x1 = current.canvasCords[0] + zeroLocX;
							y1 = current.canvasCords[1] + zeroLocY;
							dist = Math.sqrt((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y));
							if(dist <= o.multiHover) {
								found.push(current);
							}
						});
					}
					over = found;
					if(over != last) {
						if(over) {
							if(last) {
								triggerInteraction('Out', {
									point: last
								});
							}
							triggerInteraction('Over', {
								point: over
							});
							last = over;
						}
						if(last && !over) {
							triggerInteraction('Out', {
								point: last
							});
							last = false;
						}
						started = true;
					}
				});
				tracker.mouseleave(function() {
					triggerInteraction('Out', {
						point: last,
						mouseOutGraph: true
					});
					over = (last = false);
				});
			}
			if(!container) {
				canvasContain.insertAfter(this);
			}
			if(typeof(G_vmlCanvasManager) != 'undefined') {
				G_vmlCanvasManager.init();
				G_vmlCanvasManager.initElement(canvas[0]);
			}
			var ctx = canvas[0].getContext('2d');
			scroller.scrollLeft(o.width - scroller.width());
			$.each($.visualizePlugins, function(i, plugin) {
				plugin.call(self, o, tableData);
			});
			charts[o.type].setup();
			if(!container) {
				self.bind('visualizeRefresh', function() {
					self.visualize(o, $(this).empty());
				});
				self.bind('visualizeRedraw', function() {
					charts[o.type].draw();
				});
			}
		}).next();
	};
	$.visualizePlugins = [];
})(jQuery);
(function($) {
	$.visualizePlugins.push(function visualizeTooltip(options, tableData) {
		var o = $.extend({
			tooltip: false,
			tooltipalign: 'auto',
			tooltipvalign: 'top',
			tooltipclass: 'visualize-tooltip',
			tooltiphtml: function(data) {
				if(options.multiHover) {
					var html = '';
					for(var i = 0; i < data.point.length; i++) {
						html += '<p>' + data.point[i].value + ' - ' + data.point[i].yLabels[0] + '</p>';
					}
					return html;
				} else {
					return '<p>' + data.point.value + ' - ' + data.point.yLabels[0] + '</p>';
				}
			},
			delay: false
		}, options);
		if(!o.tooltip) {
			return;
		}
		var self = $(this),
			canvasContain = self.next(),
			scroller = canvasContain.find('.visualize-scroller'),
			scrollerW = scroller.width(),
			tracker = canvasContain.find('.visualize-interaction-tracker');
		tracker.css({
			backgroundColor: 'white',
			opacity: 0,
			zIndex: 100
		});
		var tooltip = $('<div class="' + o.tooltipclass + '"/>').css({
			position: 'absolute',
			display: 'none',
			zIndex: 90
		}).insertAfter(scroller.find('canvas'));
		var usescroll = true;
		if(typeof(G_vmlCanvasManager) != 'undefined') {
			scroller.css({
				'position': 'absolute'
			});
			tracker.css({
				marginTop: '-' + (o.height) + 'px'
			});
		}
		self.bind('vizualizeOver', function visualizeTooltipOver(e, data) {
			if(data.canvasContain.get(0) != canvasContain.get(0)) {
				return;
			}
			if(o.multiHover) {
				var p = data.point[0].canvasCords;
			} else {
				var p = data.point.canvasCords;
			}
			var left, right, top, clasRem, clasAd, bottom, x = Math.round(p[0] + data.tableData.zeroLocX),
				y = Math.round(p[1] + data.tableData.zeroLocY);
			if(o.tooltipalign == 'left' || (o.tooltipalign == 'auto' && x - scroller.scrollLeft() <= scrollerW / 2)) {
				if($.browser.msie && ($.browser.version == 7 || $.browser.version == 6)) {
					usescroll = false;
				} else {
					usescroll = true;
				}
				left = x - (usescroll ? scroller.scrollLeft() : 0);
				if(x - scroller.scrollLeft() < 0) {
					return;
				}
				left = left + 'px';
				right = '';
				clasAdd = "tooltipleft";
				clasRem = "tooltipright";
			} else {
				if($.browser.msie && $.browser.version == 7) {
					usescroll = false;
				} else {
					usescroll = true;
				}
				right = Math.abs(x - o.width) - (o.width - (usescroll ? scroller.scrollLeft() : 0) - scrollerW);
				if(Math.abs(x - o.width) - (o.width - scroller.scrollLeft() - scrollerW) < 0) {
					return;
				}
				left = '';
				right = right + 'px';
				clasAdd = "tooltipright";
				clasRem = "tooltipleft";
			}
			tooltip.addClass(clasAdd).removeClass(clasRem).html(o.tooltiphtml(data)).css({
				display: 'block',
				top: y + 'px',
				left: left,
				right: right
			});
		});
		self.bind('vizualizeOut', function visualizeTooltipOut(e, data) {
			tooltip.css({
				display: 'none'
			});
		});
	});
})(jQuery);;
(function(a) {
	a.uniform = {
		options: {
			selectClass: "selector",
			radioClass: "radio",
			checkboxClass: "checker",
			fileClass: "uploader",
			filenameClass: "filename",
			fileBtnClass: "action",
			fileDefaultText: "No file selected",
			fileBtnText: "Choose File",
			checkedClass: "checked",
			focusClass: "focus",
			disabledClass: "disabled",
			buttonClass: "button",
			activeClass: "active",
			hoverClass: "hover",
			useID: true,
			idPrefix: "uniform",
			resetSelector: false,
			autoHide: true
		},
		elements: []
	};
	if(a.browser.msie && a.browser.version < 7) {
		a.support.selectOpacity = false
	} else {
		a.support.selectOpacity = true
	}
	a.fn.uniform = function(k) {
		k = a.extend(a.uniform.options, k);
		var d = this;
		if(k.resetSelector != false) {
			a(k.resetSelector).mouseup(function() {
				function l() {
					a.uniform.update(d)
				}
				setTimeout(l, 10)
			})
		}

		function j(l) {
			$el = a(l);
			$el.addClass($el.attr("type"));
			b(l)
		}

		function g(l) {
			a(l).addClass("uniform");
			b(l)
		}

		function i(o) {
			var m = a(o);
			var p = a("<div>"),
				l = a("<span>");
			p.addClass(k.buttonClass);
			if(k.useID && m.attr("id") != "") {
				p.attr("id", k.idPrefix + "-" + m.attr("id"))
			}
			var n;
			if(m.is("a") || m.is("button")) {
				n = m.text()
			} else {
				if(m.is(":submit") || m.is(":reset") || m.is("input[type=button]")) {
					n = m.attr("value")
				}
			}
			n = n == "" ? m.is(":reset") ? "Reset" : "Submit" : n;
			l.html(n);
			m.css("opacity", 0);
			m.wrap(p);
			m.wrap(l);
			p = m.closest("div");
			l = m.closest("span");
			if(m.is(":disabled")) {
				p.addClass(k.disabledClass)
			}
			p.bind({
				"mouseenter.uniform": function() {
					p.addClass(k.hoverClass)
				},
				"mouseleave.uniform": function() {
					p.removeClass(k.hoverClass);
					p.removeClass(k.activeClass)
				},
				"mousedown.uniform touchbegin.uniform": function() {
					p.addClass(k.activeClass)
				},
				"mouseup.uniform touchend.uniform": function() {
					p.removeClass(k.activeClass)
				},
				"click.uniform touchend.uniform": function(r) {
					if(a(r.target).is("span") || a(r.target).is("div")) {
						if(o[0].dispatchEvent) {
							var q = document.createEvent("MouseEvents");
							q.initEvent("click", true, true);
							o[0].dispatchEvent(q)
						} else {
							o[0].click()
						}
					}
				}
			});
			o.bind({
				"focus.uniform": function() {
					p.addClass(k.focusClass)
				},
				"blur.uniform": function() {
					p.removeClass(k.focusClass)
				}
			});
			a.uniform.noSelect(p);
			b(o)
		}

		function e(o) {
			var m = a(o);
			var p = a("<div />"),
				l = a("<span />");
			if(!m.css("display") == "none" && k.autoHide) {
				p.hide()
			}
			p.addClass(k.selectClass);
			if(k.useID && o.attr("id") != "") {
				p.attr("id", k.idPrefix + "-" + o.attr("id"))
			}
			var n = o.find(":selected:first");
			if(n.length == 0) {
				n = o.find("option:first")
			}
			l.html(n.html());
			o.css("opacity", 0);
			o.wrap(p);
			o.before(l);
			p = o.parent("div");
			l = o.siblings("span");
			o.bind({
				"change.uniform": function() {
					l.text(o.find(":selected").html());
					p.removeClass(k.activeClass)
				},
				"focus.uniform": function() {
					p.addClass(k.focusClass)
				},
				"blur.uniform": function() {
					p.removeClass(k.focusClass);
					p.removeClass(k.activeClass)
				},
				"mousedown.uniform touchbegin.uniform": function() {
					p.addClass(k.activeClass)
				},
				"mouseup.uniform touchend.uniform": function() {
					p.removeClass(k.activeClass)
				},
				"click.uniform touchend.uniform": function() {
					p.removeClass(k.activeClass)
				},
				"mouseenter.uniform": function() {
					p.addClass(k.hoverClass)
				},
				"mouseleave.uniform": function() {
					p.removeClass(k.hoverClass);
					p.removeClass(k.activeClass)
				},
				"keyup.uniform": function() {
					l.text(o.find(":selected").html())
				}
			});
			if(a(o).attr("disabled")) {
				p.addClass(k.disabledClass)
			}
			a.uniform.noSelect(l);
			b(o)
		}

		function f(n) {
			var m = a(n);
			var o = a("<div />"),
				l = a("<span />");
			if(!m.css("display") == "none" && k.autoHide) {
				o.hide()
			}
			o.addClass(k.checkboxClass);
			if(k.useID && n.attr("id") != "") {
				o.attr("id", k.idPrefix + "-" + n.attr("id"))
			}
			a(n).wrap(o);
			a(n).wrap(l);
			l = n.parent();
			o = l.parent();
			a(n).css("opacity", 0).bind({
				"focus.uniform": function() {
					o.addClass(k.focusClass)
				},
				"blur.uniform": function() {
					o.removeClass(k.focusClass)
				},
				"click.uniform touchend.uniform": function() {
					if(!a(n).attr("checked")) {
						l.removeClass(k.checkedClass)
					} else {
						l.addClass(k.checkedClass)
					}
				},
				"mousedown.uniform touchbegin.uniform": function() {
					o.addClass(k.activeClass)
				},
				"mouseup.uniform touchend.uniform": function() {
					o.removeClass(k.activeClass)
				},
				"mouseenter.uniform": function() {
					o.addClass(k.hoverClass)
				},
				"mouseleave.uniform": function() {
					o.removeClass(k.hoverClass);
					o.removeClass(k.activeClass)
				}
			});
			if(a(n).attr("checked")) {
				l.addClass(k.checkedClass)
			}
			if(a(n).attr("disabled")) {
				o.addClass(k.disabledClass)
			}
			b(n)
		}

		function c(n) {
			var m = a(n);
			var o = a("<div />"),
				l = a("<span />");
			if(!m.css("display") == "none" && k.autoHide) {
				o.hide()
			}
			o.addClass(k.radioClass);
			if(k.useID && n.attr("id") != "") {
				o.attr("id", k.idPrefix + "-" + n.attr("id"))
			}
			a(n).wrap(o);
			a(n).wrap(l);
			l = n.parent();
			o = l.parent();
			a(n).css("opacity", 0).bind({
				"focus.uniform": function() {
					o.addClass(k.focusClass)
				},
				"blur.uniform": function() {
					o.removeClass(k.focusClass)
				},
				"click.uniform touchend.uniform": function() {
					if(!a(n).attr("checked")) {
						l.removeClass(k.checkedClass)
					} else {
						var p = k.radioClass.split(" ")[0];
						a("." + p + " span." + k.checkedClass + ":has([name='" + a(n).attr("name") + "'])").removeClass(k.checkedClass);
						l.addClass(k.checkedClass)
					}
				},
				"mousedown.uniform touchend.uniform": function() {
					if(!a(n).is(":disabled")) {
						o.addClass(k.activeClass)
					}
				},
				"mouseup.uniform touchbegin.uniform": function() {
					o.removeClass(k.activeClass)
				},
				"mouseenter.uniform touchend.uniform": function() {
					o.addClass(k.hoverClass)
				},
				"mouseleave.uniform": function() {
					o.removeClass(k.hoverClass);
					o.removeClass(k.activeClass)
				}
			});
			if(a(n).attr("checked")) {
				l.addClass(k.checkedClass)
			}
			if(a(n).attr("disabled")) {
				o.addClass(k.disabledClass)
			}
			b(n)
		}

		function h(q) {
			var o = a(q);
			var r = a("<div />"),
				p = a("<span>" + k.fileDefaultText + "</span>"),
				m = a("<span>" + k.fileBtnText + "</span>");
			if(!o.css("display") == "none" && k.autoHide) {
				r.hide()
			}
			r.addClass(k.fileClass);
			p.addClass(k.filenameClass);
			m.addClass(k.fileBtnClass);
			if(k.useID && o.attr("id") != "") {
				r.attr("id", k.idPrefix + "-" + o.attr("id"))
			}
			o.wrap(r);
			o.after(m);
			o.after(p);
			r = o.closest("div");
			p = o.siblings("." + k.filenameClass);
			m = o.siblings("." + k.fileBtnClass);
			if(!o.attr("size")) {
				var l = r.width();
				o.attr("size", l / 10)
			}
			var n = function() {
				var s = o.val();
				if(s === "") {
					s = k.fileDefaultText
				} else {
					s = s.split(/[\/\\]+/);
					s = s[(s.length - 1)]
				}
				p.text(s)
			};
			n();
			o.css("opacity", 0).bind({
				"focus.uniform": function() {
					r.addClass(k.focusClass)
				},
				"blur.uniform": function() {
					r.removeClass(k.focusClass)
				},
				"mousedown.uniform": function() {
					if(!a(q).is(":disabled")) {
						r.addClass(k.activeClass)
					}
				},
				"mouseup.uniform": function() {
					r.removeClass(k.activeClass)
				},
				"mouseenter.uniform": function() {
					r.addClass(k.hoverClass)
				},
				"mouseleave.uniform": function() {
					r.removeClass(k.hoverClass);
					r.removeClass(k.activeClass)
				}
			});
			if(a.browser.msie) {
				o.bind("click.uniform.ie7", function() {
					setTimeout(n, 0)
				})
			} else {
				o.bind("change.uniform", n)
			}
			if(o.attr("disabled")) {
				r.addClass(k.disabledClass)
			}
			a.uniform.noSelect(p);
			a.uniform.noSelect(m);
			b(q)
		}
		a.uniform.restore = function(l) {
			if(l == undefined) {
				l = a(a.uniform.elements)
			}
			a(l).each(function() {
				if(a(this).is(":checkbox")) {
					a(this).unwrap().unwrap()
				} else {
					if(a(this).is("select")) {
						a(this).siblings("span").remove();
						a(this).unwrap()
					} else {
						if(a(this).is(":radio")) {
							a(this).unwrap().unwrap()
						} else {
							if(a(this).is(":file")) {
								a(this).siblings("span").remove();
								a(this).unwrap()
							} else {
								if(a(this).is("button, :submit, :reset, a, input[type='button']")) {
									a(this).unwrap().unwrap()
								}
							}
						}
					}
				}
				a(this).unbind(".uniform");
				a(this).css("opacity", "1");
				var m = a.inArray(a(l), a.uniform.elements);
				a.uniform.elements.splice(m, 1)
			})
		};

		function b(l) {
			l = a(l).get();
			if(l.length > 1) {
				a.each(l, function(m, n) {
					a.uniform.elements.push(n)
				})
			} else {
				a.uniform.elements.push(l)
			}
		}
		a.uniform.noSelect = function(l) {
			function m() {
				return false
			}
			a(l).each(function() {
				this.onselectstart = this.ondragstart = m;
				a(this).mousedown(m).css({
					MozUserSelect: "none"
				})
			})
		};
		a.uniform.update = function(l) {
			if(l == undefined) {
				l = a(a.uniform.elements)
			}
			l = a(l);
			l.each(function() {
				var n = a(this);
				if(n.is("select")) {
					var m = n.siblings("span");
					var p = n.parent("div");
					p.removeClass(k.hoverClass + " " + k.focusClass + " " + k.activeClass);
					m.html(n.find(":selected").html());
					if(n.is(":disabled")) {
						p.addClass(k.disabledClass)
					} else {
						p.removeClass(k.disabledClass)
					}
				} else {
					if(n.is(":checkbox")) {
						var m = n.closest("span");
						var p = n.closest("div");
						p.removeClass(k.hoverClass + " " + k.focusClass + " " + k.activeClass);
						m.removeClass(k.checkedClass);
						if(n.is(":checked")) {
							m.addClass(k.checkedClass)
						}
						if(n.is(":disabled")) {
							p.addClass(k.disabledClass)
						} else {
							p.removeClass(k.disabledClass)
						}
					} else {
						if(n.is(":radio")) {
							var m = n.closest("span");
							var p = n.closest("div");
							p.removeClass(k.hoverClass + " " + k.focusClass + " " + k.activeClass);
							m.removeClass(k.checkedClass);
							if(n.is(":checked")) {
								m.addClass(k.checkedClass)
							}
							if(n.is(":disabled")) {
								p.addClass(k.disabledClass)
							} else {
								p.removeClass(k.disabledClass)
							}
						} else {
							if(n.is(":file")) {
								var p = n.parent("div");
								var o = n.siblings(k.filenameClass);
								btnTag = n.siblings(k.fileBtnClass);
								p.removeClass(k.hoverClass + " " + k.focusClass + " " + k.activeClass);
								o.text(n.val());
								if(n.is(":disabled")) {
									p.addClass(k.disabledClass)
								} else {
									p.removeClass(k.disabledClass)
								}
							} else {
								if(n.is(":submit") || n.is(":reset") || n.is("button") || n.is("a") || l.is("input[type=button]")) {
									var p = n.closest("div");
									p.removeClass(k.hoverClass + " " + k.focusClass + " " + k.activeClass);
									if(n.is(":disabled")) {
										p.addClass(k.disabledClass)
									} else {
										p.removeClass(k.disabledClass)
									}
								}
							}
						}
					}
				}
			})
		};
		return this.each(function() {
			if(a.support.selectOpacity) {
				var l = a(this);
				if(l.is("select")) {
					if(l.attr("multiple") != true) {
						if(l.attr("size") == undefined || l.attr("size") <= 1) {
							e(l)
						}
					}
				} else {
					if(l.is(":checkbox")) {
						f(l)
					} else {
						if(l.is(":radio")) {
							c(l)
						} else {
							if(l.is(":file")) {
								h(l)
							} else {
								if(l.is(":text, :password, input[type='email']")) {
									j(l)
								} else {
									if(l.is("textarea")) {
										g(l)
									} else {
										if(l.is("a") || l.is(":submit") || l.is(":reset") || l.is("button") || l.is("input[type=button]")) {
											i(l)
										}
									}
								}
							}
						}
					}
				}
			}
		})
	}
})(jQuery);;
jQuery(function($) {
	$('a.zoombox').zoombox({
		animation: false
	});
	$('.bloc .title').append('<a href="#" class="toggle"></a>');
	$('.bloc .title .tabs').parent().find('.toggle').remove();
	$('.bloc .title .toggle').click(function() {
		$(this).toggleClass('hide').parent().parent().find('.content').slideToggle(300);
		return false;
	});
	$('.wysiwyg').wysiwyg({
		autoGrow: true,
		maxHeight: 600
	});
	$('a[title!=""]').tooltipsy();
	$('table.graph').each(function() {
		var matches = $(this).attr('class').split(/type\-(area|bar|pie|line)/g);
		var options = {
			height: '300px',
			width: parseInt($(this).width()) - 100,
			colors: ['#c21c1c', '#f1dc2b', '#9ccc0a', '#0accaa', '#0a93cc', '#8734c8', '#26a4ed', '#f45a90', '#e9e744']
		};
		if(matches[1] != undefined) {
			options.type = matches[1];
		}
		if($(this).hasClass('dots')) {
			options.lineDots = 'double';
		}
		if($(this).hasClass('tips')) {
			options.interaction = true;
			options.multiHover = 15, options.tooltip = true, options.tooltiphtml = function(data) {
				var html = '';
				for(var i = 0; i < data.point.length; i++) {
					html += '<p class="stats_tooltip"><strong>' + data.point[i].value + '</strong> ' + data.point[i].yLabels[0] + '</p>';
				}
				return html;
			}
		}
		$(this).hide().visualize(options);
	});
	$('a[href^="#"][href!="#"]').click(function() {
		cible = $(this).attr('href');
		if(cible == "#") {
			return false;
		}
		scrollTo(cible);
		return false;
	});
	if(!$.browser.msie) {
		$('.iphone').iphoneStyle({
			checkedLabel: 'YES',
			uncheckedLabel: 'NO'
		});
	}
	$("select,.input input:checkbox, input:radio, input:file").uniform();
	$(".datepicker").datepicker();
	$('.range').each(function() {
		var cls = $(this).attr('class');
		var matches = cls.split(/([a-zA-Z]+)\-([0-9]+)/g);
		var options = {
			animate: true
		};
		var elem = $(this).parent();
		elem.append('<div class="uirange"></div>');
		for(i in matches) {
			i = i * 1;
			if(matches[i] == 'max') {
				options.max = matches[i + 1] * 1
			}
			if(matches[i] == 'min') {
				options.min = matches[i + 1] * 1
			}
		}
		options.slide = function(event, ui) {
			elem.find('span:first').empty().append(ui.value);
			elem.find('input:first').val(ui.value);
		}
		elem.find('span:first').empty().append(elem.find('input:first').val());
		options.range = 'min';
		options.value = elem.find('input:first').val();
		elem.find('.uirange').slider(options);
		$(this).hide();
	});
	$('.input.error input,.input textarea,.input select').focus(function() {
		$(this).parent().removeClass('error');
		$(this).parent().find('.error-message').fadeTo(500, 0).slideUp();
		$(this).unbind('focus');
	});
	$('.notif .close').click(function() {
		$(this).parent().fadeTo(500, 0).slideUp();
		return false;
	});
	var anchor = window.location.hash;
	$('.tabs').each(function() {
		var current = null;
		var id = $(this).attr('id');
		if(anchor != '' && $(this).find('a[href="' + anchor + '"]').length > 0) {
			current = anchor;
		} else if($.cookie('tab' + id) && $(this).find('a[href="' + $.cookie('tab' + id) + '"]').length > 0) {
			current = $.cookie('tab' + id);
		} else {
			current = $(this).find('a:first').attr('href');
		}
		$(this).find('a[href="' + current + '"]').addClass('active');
		$(current).siblings().hide();
		$(this).find('a').click(function() {
			var link = $(this).attr('href');
			if(link == current) {
				return false;
			} else {
				$(this).addClass('active').siblings().removeClass('active');
				$(link).show().siblings().hide();
				current = link;
				$.cookie('tab' + id, current);
			}
		});
	});
	$('#content .checkall').change(function() {
		$(this).parents('table:first').find('input').attr('checked', $(this).is(':checked'));
	});
	var currentMenu = null;
	$('#sidebar>ul>li').each(function() {
		if($(this).find('li').length == 0) {
			$(this).addClass('nosubmenu');
		}
	})
	$('#sidebar>ul>li:not([class*="current"])>ul').hide();
	$('#sidebar>ul>li:not([class*="nosubmenu"])>a').click(function() {
		e = $(this).parent();
		$('#sidebar>ul>li.current').removeClass('current').find('ul:first').slideUp();
		e.addClass('current').find('ul:first').slideDown();
	});
	var htmlCollapse = $('#menucollapse').html();
	if($.cookie('isCollapsed')) {
		$('body').addClass('collapsed');
		$('#menucollapse').html('&#9654;');
	}
	$('#menucollapse').click(function() {
		var body = $('body');
		body.toggleClass('collapsed');
		isCollapsed = body.hasClass('collapsed');
		if(isCollapsed) {
			$(this).html('&#9654;');
		} else {
			$(this).html(htmlCollapse);
		}
		$.cookie('isCollapsed', isCollapsed);
		return false;
	});
	$('.placeholder,#content.login .input').each(function() {
		var label = $(this).find('label:first');
		var input = $(this).find('input:first,textarea:first');
		if(input.val() != '') {
			label.stop().hide();
		}
		input.focus(function() {
			if($(this).val() == '') {
				label.stop().fadeTo(500, 0.5);
			}
			$(this).parent().removeClass('error').find('.error-message').fadeOut();
		});
		input.blur(function() {
			if($(this).val() == '') {
				label.stop().fadeTo(500, 1);
			}
		});
		input.keypress(function() {
			label.stop().hide();
		});
		input.keyup(function() {
			if($(this).val() == '') {
				label.stop().fadeTo(500, 0.5);
			}
		});
		input.bind('cut copy paste', function(e) {
			label.stop().hide();
		});
	});
	$('.close').click(function() {
		$(this).parent().fadeTo(500, 0).slideUp();
	});
	$(window).resize(function() {
		$('.center').each(function() {
			$(this).css('display', 'inline');
			var width = $(this).width();
			if(parseInt($(this).height()) < 100) {
				$(this).css({
					width: 'auto'
				});
			} else {
				$(this).css({
					width: width
				});
			}
			$(this).css('display', 'block');
		});
		$('.calendar td').height($('.calendar td[class!="padding"]').width());
	});
	$(window).trigger('resize');

	function scrollTo(cible) {
		if($(cible).length >= 1) {
			hauteur = $(cible).offset().top;
		} else {
			return false;
		}
		hauteur -= (windowH() - $(cible).height()) / 2;
		$('html,body').animate({
			scrollTop: hauteur
		}, 1000, 'easeOutQuint');
		return false;
	}

	function windowH() {
		if(window.innerHeight) return window.innerHeight;
		else {
			return $(window).height();
		}
	}
});
jQuery.easing['jswing'] = jQuery.easing['swing'];
jQuery.extend(jQuery.easing, {
	def: 'easeOutQuad',
	swing: function(x, t, b, c, d) {
		return jQuery.easing[jQuery.easing.def](x, t, b, c, d);
	},
	easeInQuad: function(x, t, b, c, d) {
		return c * (t /= d) * t + b;
	},
	easeOutQuad: function(x, t, b, c, d) {
		return -c * (t /= d) * (t - 2) + b;
	},
	easeInOutQuad: function(x, t, b, c, d) {
		if((t /= d / 2) < 1) return c / 2 * t * t + b;
		return -c / 2 * ((--t) * (t - 2) - 1) + b;
	},
	easeInCubic: function(x, t, b, c, d) {
		return c * (t /= d) * t * t + b;
	},
	easeOutCubic: function(x, t, b, c, d) {
		return c * ((t = t / d - 1) * t * t + 1) + b;
	},
	easeInOutCubic: function(x, t, b, c, d) {
		if((t /= d / 2) < 1) return c / 2 * t * t * t + b;
		return c / 2 * ((t -= 2) * t * t + 2) + b;
	},
	easeInQuart: function(x, t, b, c, d) {
		return c * (t /= d) * t * t * t + b;
	},
	easeOutQuart: function(x, t, b, c, d) {
		return -c * ((t = t / d - 1) * t * t * t - 1) + b;
	},
	easeInOutQuart: function(x, t, b, c, d) {
		if((t /= d / 2) < 1) return c / 2 * t * t * t * t + b;
		return -c / 2 * ((t -= 2) * t * t * t - 2) + b;
	},
	easeInQuint: function(x, t, b, c, d) {
		return c * (t /= d) * t * t * t * t + b;
	},
	easeOutQuint: function(x, t, b, c, d) {
		return c * ((t = t / d - 1) * t * t * t * t + 1) + b;
	},
	easeInOutQuint: function(x, t, b, c, d) {
		if((t /= d / 2) < 1) return c / 2 * t * t * t * t * t + b;
		return c / 2 * ((t -= 2) * t * t * t * t + 2) + b;
	},
	easeInSine: function(x, t, b, c, d) {
		return -c * Math.cos(t / d * (Math.PI / 2)) + c + b;
	},
	easeOutSine: function(x, t, b, c, d) {
		return c * Math.sin(t / d * (Math.PI / 2)) + b;
	},
	easeInOutSine: function(x, t, b, c, d) {
		return -c / 2 * (Math.cos(Math.PI * t / d) - 1) + b;
	},
	easeInExpo: function(x, t, b, c, d) {
		return(t == 0) ? b : c * Math.pow(2, 10 * (t / d - 1)) + b;
	},
	easeOutExpo: function(x, t, b, c, d) {
		return(t == d) ? b + c : c * (-Math.pow(2, -10 * t / d) + 1) + b;
	},
	easeInOutExpo: function(x, t, b, c, d) {
		if(t == 0) return b;
		if(t == d) return b + c;
		if((t /= d / 2) < 1) return c / 2 * Math.pow(2, 10 * (t - 1)) + b;
		return c / 2 * (-Math.pow(2, -10 * --t) + 2) + b;
	},
	easeInCirc: function(x, t, b, c, d) {
		return -c * (Math.sqrt(1 - (t /= d) * t) - 1) + b;
	},
	easeOutCirc: function(x, t, b, c, d) {
		return c * Math.sqrt(1 - (t = t / d - 1) * t) + b;
	},
	easeInOutCirc: function(x, t, b, c, d) {
		if((t /= d / 2) < 1) return -c / 2 * (Math.sqrt(1 - t * t) - 1) + b;
		return c / 2 * (Math.sqrt(1 - (t -= 2) * t) + 1) + b;
	},
	easeInElastic: function(x, t, b, c, d) {
		var s = 1.70158;
		var p = 0;
		var a = c;
		if(t == 0) return b;
		if((t /= d) == 1) return b + c;
		if(!p) p = d * .3;
		if(a < Math.abs(c)) {
			a = c;
			var s = p / 4;
		} else var s = p / (2 * Math.PI) * Math.asin(c / a);
		return -(a * Math.pow(2, 10 * (t -= 1)) * Math.sin((t * d - s) * (2 * Math.PI) / p)) + b;
	},
	easeOutElastic: function(x, t, b, c, d) {
		var s = 1.70158;
		var p = 0;
		var a = c;
		if(t == 0) return b;
		if((t /= d) == 1) return b + c;
		if(!p) p = d * .3;
		if(a < Math.abs(c)) {
			a = c;
			var s = p / 4;
		} else var s = p / (2 * Math.PI) * Math.asin(c / a);
		return a * Math.pow(2, -10 * t) * Math.sin((t * d - s) * (2 * Math.PI) / p) + c + b;
	},
	easeInOutElastic: function(x, t, b, c, d) {
		var s = 1.70158;
		var p = 0;
		var a = c;
		if(t == 0) return b;
		if((t /= d / 2) == 2) return b + c;
		if(!p) p = d * (.3 * 1.5);
		if(a < Math.abs(c)) {
			a = c;
			var s = p / 4;
		} else var s = p / (2 * Math.PI) * Math.asin(c / a);
		if(t < 1) return -.5 * (a * Math.pow(2, 10 * (t -= 1)) * Math.sin((t * d - s) * (2 * Math.PI) / p)) + b;
		return a * Math.pow(2, -10 * (t -= 1)) * Math.sin((t * d - s) * (2 * Math.PI) / p) * .5 + c + b;
	},
	easeInBack: function(x, t, b, c, d, s) {
		if(s == undefined) s = 1.70158;
		return c * (t /= d) * t * ((s + 1) * t - s) + b;
	},
	easeOutBack: function(x, t, b, c, d, s) {
		if(s == undefined) s = 1.70158;
		return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
	},
	easeInOutBack: function(x, t, b, c, d, s) {
		if(s == undefined) s = 1.70158;
		if((t /= d / 2) < 1) return c / 2 * (t * t * (((s *= (1.525)) + 1) * t - s)) + b;
		return c / 2 * ((t -= 2) * t * (((s *= (1.525)) + 1) * t + s) + 2) + b;
	},
	easeInBounce: function(x, t, b, c, d) {
		return c - jQuery.easing.easeOutBounce(x, d - t, 0, c, d) + b;
	},
	easeOutBounce: function(x, t, b, c, d) {
		if((t /= d) < (1 / 2.75)) {
			return c * (7.5625 * t * t) + b;
		} else if(t < (2 / 2.75)) {
			return c * (7.5625 * (t -= (1.5 / 2.75)) * t + .75) + b;
		} else if(t < (2.5 / 2.75)) {
			return c * (7.5625 * (t -= (2.25 / 2.75)) * t + .9375) + b;
		} else {
			return c * (7.5625 * (t -= (2.625 / 2.75)) * t + .984375) + b;
		}
	},
	easeInOutBounce: function(x, t, b, c, d) {
		if(t < d / 2) return jQuery.easing.easeInBounce(x, t * 2, 0, c, d) * .5 + b;
		return jQuery.easing.easeOutBounce(x, t * 2 - d, 0, c, d) * .5 + c * .5 + b;
	}
});