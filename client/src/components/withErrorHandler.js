import React from 'react'
import ErrorHandler from 'E-....'
const UtilsContext = React.createContext()
export default UtilsContext

/**
 * This function follows the higher-order component (HOC) pattern.
 * The utilsObj property that is added to the given component provides utility functions like: fetching (HTTP GET 
 * request); making any other HTTP request; making an HTTP request based on a HAL+forms object.
 * 
 * @param {object} Component - Represents the wrapped React component to add the utilsObj property.
 */
 export const withErrorHandler = Component => (
        <ErrorHandler>
            {
                handleError => <Component handleError={handleError} />
            }
        </ErrorHandler>
)