


/**
 * Converts miles to kilometers
 * 
 * @param {*} miles 
 * @returns distance in kilometers
 */
function milesToKilometers(miles) {
    return miles * 1.60934;
}

/**
 * Converts kilometers to miles
 * 
 * @param {*} kilometers 
 * @returns distance in miles
 */
function kilometersToMiles(kilometers) {
    return kilometers / 1.60934;
}

export { milesToKilometers, kilometersToMiles };