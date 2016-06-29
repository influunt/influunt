/*
||
|| @file Utility.h
|| @version 1.2
|| @author Alexander Brevig
|| @contact alexanderbrevig@gmail.com
||
|| @description
|| | Utility functions for the Arduino
|| #
||
|| @license
|| | This library is free software; you can redistribute it and/or
|| | modify it under the terms of the GNU Lesser General Public
|| | License as published by the Free Software Foundation; version
|| | 2.1 of the License.
|| |
|| | This library is distributed in the hope that it will be useful,
|| | but WITHOUT ANY WARRANTY; without even the implied warranty of
|| | MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
|| | Lesser General Public License for more details.
|| |
|| | You should have received a copy of the GNU Lesser General Public
|| | License along with this library; if not, write to the Free Software
|| | Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
|| #
||
*/

#ifndef UTILITY_H
#define UTILITY_H

#include <Arduino.h>

template <typename type, typename param1, typename param2, typename mod>
void foreach(type* pins, byte numberOfPins, void (*function)(param1,param2),mod modifier){
	for (byte i=0; i<numberOfPins; i++){
		function(pins[i],modifier);
	}
}

template <typename type, typename param1, typename param2, typename mod>
void foreach(type* pins, int from, int to, void (*function)(param1,param2),mod modifier){
	for (byte i=from; i<=to; i++){
		function(pins[i],modifier);
	}
}

template <typename type, typename type2, typename param1>
void foreach(type* pins, type2* result, int numberOfPins, int (*function)(param1)){
	for (byte i=0; i<numberOfPins; i++){
		result[i] = function(pins[i]);
	}
}

//determine the sign of a value
template <typename type>
type sign(type value) { 
  return type((value>0)-(value<0)); 
}

#endif

/*
|| @changelog
|| | 1.2 2009-06-17 - Alexander Brevig : extended templating
|| | 1.1 2009-06-17 - Alexander Brevig : sign
|| | 1.0 2009-06-17 - Alexander Brevig : foreach
|| | 1.0 2009-06-17 - Alexander Brevig : Initial Release
|| #
*/