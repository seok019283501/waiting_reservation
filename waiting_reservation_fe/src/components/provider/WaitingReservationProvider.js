import { useEffect, useRef, useState } from "react";
import WaitingReservationContext from "./WaitingReservationContext";
import axios from "axios";
const WaitingReservationProvider = (props) =>{
  const [role,setRole] = useState("ROLE_USER");
  const [reaminCount, setReaminCount] = useState("");
  const [userList,setUserList] = useState([]);
  const [orderMenuList,setOrderMenuList] = useState([]);


  //주문
  const order = (id) =>{
    console.log(orderMenuList)
    const tk = localStorage.getItem("jwt")
    axios.post((`http://localhost:8080/api/order/${id}`),
    orderMenuList,{
      headers:{
        Authorization: tk
      }
    }).then(res=>{
      console.log(res)
    }).catch(err=>{
      console.log(err);
    })
  }

  const handleSetOrderMenuList=(item)=>{
    orderMenuList.push(item);
    setOrderMenuList(orderMenuList);
  }

  //대기자 조회
  const waitingUserList = (id) =>{
    const tk = localStorage.getItem("jwt")

    axios.get((`http://localhost:8080/api/reservation/list/${id}`),{
      headers:{
        Authorization: tk
      }
    }).then(res=>{
      console.log(res)
      setUserList(res.data.body);
      setReaminCount(res.data.body.length);
    }).catch(err=>{
      console.log(err);
    })
  }
  
  const insertRole = role =>{
    setRole(role)
  }

  return (
    <WaitingReservationContext.Provider value={{insertRole, role,reaminCount,userList,waitingUserList,order,handleSetOrderMenuList}}>
        {props.children}
    </WaitingReservationContext.Provider>
  );
};

export default WaitingReservationProvider;