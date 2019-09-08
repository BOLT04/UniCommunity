/**
 * Setup required by enzyme
 * @see https://airbnb.io/enzyme/docs/installation/index.html Working with React 16 section.
 */ 
import { configure } from 'enzyme'
import Adapter from 'enzyme-adapter-react-16'
configure({ adapter: new Adapter() })