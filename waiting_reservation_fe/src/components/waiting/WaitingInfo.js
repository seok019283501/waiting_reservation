import { useEffect, useState, useContext } from 'react';
import '../../styles/Waiting.css'
import CommonItem from './CommonItem';
import axios from "axios";
import WaitingReservationContext from "../provider/WaitingReservationContext";
const WaitingInfo = (props) =>{
  const [reaminCount, setReaminCount] = useState("");
  const [menuList,setMenuList] = useState([]);
  const [storeId, setStoreId] = useState("");
  const {order} = useContext(WaitingReservationContext);
  useEffect(()=>{
    const tk = localStorage.getItem("jwt")
    axios.get((`http://localhost:8080/api/reservation/info`),{
      headers:{
        Authorization: tk
      }
    }).then(res=>{
      console.log(res)
      setReaminCount(res.data.body.remainingCount);
      setStoreId(res.data.body.storeId)
      menuInfo(res.data.body.storeId);
    }).catch(err=>{
      console.log(err);
    })
  },[])

  //메뉴 정보 조회
  const menuInfo = (storeId) =>{
    const tk = localStorage.getItem("jwt")
    axios.get((`http://localhost:8080/open-api/menu/${storeId}`))
    .then(res=>{
      console.log(res.data.body);
      setMenuList(res.data.body);
    }).catch(err=>{
      console.log(err);
    })
  }

  //예약 정보 조회
  const check=()=>{
    const tk = localStorage.getItem("jwt")
    axios.get((`http://localhost:8080/api/reservation/info`),{
      headers:{
        Authorization: tk
      }
    }).then(res=>{
      console.log(res)
      setReaminCount(res.data.body.remainingCount);
    }).catch(err=>{
      console.log(err);
    })
  }

  return (
    <>
      <div className="Waiting-container">
        <div className='Waiting-count-container'>
          <div className='Waiting-count-sub-container'>
            <div className='Waiting-count-info-container'>
              {
                reaminCount === "" || reaminCount ===undefined ? 
                  '-' : `남은 순서 : ${reaminCount}`
              }
            </div>
            <div className='Waiting-count-info-button-container'>
            <input type='button' className='count-btn' onClick={check} value={'조회'}/>
            <input type='button' className='menu-btn' value={"주문"} onClick={()=>order(storeId)}/>

            </div>
          </div>
        </div>
        <div className='Waiting-menu-order-container'>
          <div className='Waiting-menu-list-container'>
            {
              menuList.map((item)=>(
                <CommonItem key={item.id} item={item}/>
              ))
            }
          </div>
          
        </div>
      </div>
    </>
  );
}

export default WaitingInfo;