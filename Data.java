//    +-----+  +-----+  +-----+     1: outside   lantern
//    |     |  |     |  |     |     2: theater   trapdoor
//    |  3  +--+  1  +--+  2  |     3: pub       beer
//    |     |  |     |  |     |     4: lab
//    +-----+  +--+--+  +-----+     5: office    keys
//                |
//             +--+--+  +-----+         N
//             |     |  |     |         ^
//             |  4  +--+  5  |      W<--->E
//             |     |  |     |         v
//             +-----+  +-----+         S
//    
var data = {
	player: 
		{ start: 1 }
	,
	rooms: [ // #, description
		[1, 'outside the main entrance of the university'],
		[2, 'in a lecture theater'],
		[3, 'in the campus pub'],
		[4, 'in a computing lab'],
		[5, 'in the computing admin office']
	],
	exits: [ // from, to, command, list of conditions
		[1, 2, 'east', 1],
		[1, 4, 'south', 1],
		[1, 3, 'west', 1],
		[2, 1, 'west', 1],
		[3, 1, 'east', 1],
		[4, 1, 'north', 'pct:50'], // going back will work only half the time
		[4, 5, 'east', 'gate:0'], // gate needs not to be at state 0
		[5, 4, 'west', 'gate:0']
	],
	items: [ // name, message (status0), initial room, optional: fixed room# (or -1), list of properties
		['lamp', ['a lantern'], 1],
		['keys', ['a set of keys'], 4, 'can:lock', 'can:unlock'],
		['picture', ['a picture'], 2, -1],
		['beer', ['a pint of beer'], 3],
		['gate', ['a locked door with steel bars', 'an open door with steel bars'], 4, 5, 'has:lock:0', 'has:unlock:1'],
	],
	words: [ // type, verb, list of synonyms
		// 0: direction, 1: action, 2: items
		[0, 'north', 'n'],
		[0, 'south', 's'],
		[0, 'east','e'],
		[0, 'west', 'w'],
		[0, 'northeast', 'ne'],
		[0, 'northwest', 'nw'],
		[0, 'southeast', 'se'],
		[0, 'southwest', 'sw'],    
		[0, 'down', 'd', 'downward', 'downwards'],
		[0, 'up', 'u', 'upward', 'upwards'],
		[0, 'in', 'inward', 'inside', 'enter'],
		[0, 'out', 'outside', 'exit', 'leave'],  
		[1, 'take', 'get', 'carry', 'keep', 'catch', 'steal', 'capture', 'tote'],
		[1, 'drop', 'release', 'free', 'discard', 'dump'],
		[1, 'unlock', 'open'],
		[1, 'lock', 'close'],
		[1, 'on'],
		[1, 'off', 'extinguish' ],
		[1, 'say', 'chant', 'sing', 'utter', 'mumble', 'talk'],
		[1, 'wave', 'shake', 'swing'],
		[1, 'calm', 'tame', 'soften', 'subdue', 'placate', 'ease'],
		[1, 'go', 'walk', 'run', 'travel', 'proceed', 'continue', 'explore', 'goto', 'follow', 'turn'],
		[1, 'attack', 'kill', 'fight', 'hit', 'strike'],
		[1, 'eat', 'devour', 'swallow'],
		[1, 'drink'],
		[1, 'throw', 'toss'],
		[1, 'find', 'where'],
		[1, 'rub'],
		[1, 'fill'],
		[1, 'blast', 'detonate', 'ignite', 'blowup'],
		[1, 'score'],
		[1, 'brief'],
		[1, 'read', 'peruse'],
		[1, 'break', 'shatter', 'smash'],
		[1, 'inventory'],
		[1, 'look', 'examine', 'touch', 'describe', 'search', 'find'],
		[1, 'log'],
		[1, 'quit', 'stop', 'leave'],
		[1, 'help'],
		[2, 'keys', 'key'],
		[2, 'axe'],
		[2, 'gold', 'bar'],
		[2, 'book'],
		[2, 'database'],
		[2, 'gate', 'grate', 'bars', 'door', 'trapdoor'],
		[2, 'lamp', 'light', 'lantern'],
		[2, 'wand', 'magic wand'],
		[2, 'picture', 'painting', 'portret'],
		[2, 'beer', 'pint']
	],
	messages: [
	]
};
