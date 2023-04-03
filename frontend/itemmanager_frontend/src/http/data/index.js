import axios from "../request";

export const postdata=(data,url)=>{
    return axios({
        url:url,
        method:'post',
        data
    })
}

export default {postdata}