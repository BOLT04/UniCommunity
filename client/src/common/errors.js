const DEFAULT_MAPPING_MSG = 'An error occured while mapping the response from the API to an object.'
const DEFAULT_LOGOUT_MSG = 'An error occured while logging out.'

export function MappingError(message) {
    Error.apply(this, message || DEFAULT_MAPPING_MSG)
}

export function LogoutError(message) {
    Error.apply(this, message || DEFAULT_LOGOUT_MSG)
}

/*
export function PermissionError(message) {
    Error.apply(this, message || ?)
}

export function DatabaseError(message) {
    Error.apply(this, message || ?)
}
*/