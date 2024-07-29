import { useEffect, useState,useContext } from 'react';
import '../../styles/Waiting.css'
import OrderItem from './OrderItem';
import axios from "axios";
import { useLocation } from 'react-router-dom';
import WaitingReservationContext from "../provider/WaitingReservationContext";
const Order = (props) =>{
  const location = useLocation();
  //주문 내역
  const [menuList,setMenuList] = useState([]);
  //총가격
  const [totalCost, setTotalCost] = useState(0);


  useEffect(()=>{
    order();
  },[])

  //주문 내역 확인
  const order =()=>{
    const tk = localStorage.getItem("jwt")
    axios.get((`http://localhost:8080/api/order/list/${location.state.storeId}?username=${location.state.username}`),{
      headers:{
        Authorization: tk
      }
    }).then(res=>{
      console.log(res)
      setMenuList(res.data.body);

    }).catch(err=>{
      console.log(err);
    })
  }

  //총가격
  useEffect(()=>{
    let c = 0;
    menuList.map(it=>{
      c+=it.cost
    })
    setTotalCost(c);
  },[menuList]);
  



  return (
    <>
      <div className="Waiting-container">
        <div className='Waiting-count-container'>
          <div className='Waiting-count-sub-container'>
            <div className='Waiting-count-info-container'>
              {location.state.name}님 주문 내역
            </div>
            <div className='Waiting-count-info-container'>
              {totalCost}원
            </div>
          </div>
        </div>
        <div className='Waiting-menu-order-container'>
          <div className='Waiting-menu-list-container'>
              {
                menuList.map((item)=>(
                  <OrderItem key={item.id} item={item}/>
                ))
              }
          </div>
        </div>
        
      </div>
    </>
  );
}

export default Order;