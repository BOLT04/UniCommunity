import HomeApi from './HomeApi'

export default class HomeApiImpl extends HomeApi {

    fetchHomeAsync = async (url) => {
        return await fetch(url)
    }
}
