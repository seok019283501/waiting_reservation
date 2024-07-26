import { useEffect, useRef, useState } from "react";
import WaitingReservationContext from "./WaitingReservationContext";
import axios from "axios";
const WaitingReservationProvider = (props) =>{
  const [todos, setTodos] = useState([
    { id: 1, checked: true, text: '자바스크립트 공부하기' },
    { id: 2, checked: false, text: '리액트 공부하기' },
    { id: 3, checked: false, text: '할일 목록 앱 만들기' },
  ]);
  //토큰
  const [token,setToken] = useState("");
  //user 비밀번호
  const [idPassword, setIdPassword] = useState({
    id:"",
    password:""
  });
  //user 정보
  const [userInfo, setUserInfo] = useState([]);

  const nextId = useRef(4);
  const insertIdPassword = (id,password) =>{
    setIdPassword([...setIdPassword,{id:id, password:password}])
  }
  useEffect(()=>{
    console.log(idPassword);
  },[idPassword])

  const login = ()=>{
    axios.post((`http://localhost:8080/open-api/user/login`),
      idPassword
    ).then(res=>{
      console.log(res);
    }).catch(err=>{
      console.log(err);
    })
  }
  const logout = ()=>{
    setToken("");
  }
    

  return (
    <WaitingReservationContext.Provider value={{userInfo, login, logout, insertIdPassword}}>
        {props.children}
    </WaitingReservationContext.Provider>
  );
};

export default WaitingReservationProvider;