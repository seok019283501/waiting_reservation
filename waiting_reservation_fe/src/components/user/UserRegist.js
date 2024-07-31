import { useEffect, useState, useContext } from 'react';
import { useNavigate } from "react-router-dom";
import '../../styles/UserRegist.css'
import axios from "axios";
const UserRegist = (props) =>{
  const list = ["고객","사업자"];
  const [role,setRole] = useState("ROLE_USER");
  const [confirmPassword,setConfirmPassword] = useState("");
  const [passwordTF,setPasswordTF] = useState(true);
  const [confirmRegist,setConfirmRegist] = useState(true);
  const [user,setUser] = useState({
    name:"",
    username:"",
    password: "",
    email:"",
    phoneNumber:"",
    birth:""
  })
  const navigator = useNavigate();

  

  const regist=()=>{
    axios.post((`http://localhost:8080/open-api/user/register/${role}`),user
    ).then(res=>{
      navigator("/");
    }).catch(err=>{
      console.log(err);
    })
  }

  //역할 설정
  const handleSetRole = (e) =>{
    if(e.target.value === "고객"){
      setRole("ROLE_USER");
    }else if(e.target.value === "사업가"){
      setRole("ROLE_OWNER");
    }
  }

  //비밀번호 비교
  useEffect(()=>{
    if(confirmPassword === user.password){
      setPasswordTF(true);
    }else{
      setPasswordTF(false);
    }
  },[confirmPassword,user])

  //무결성
  useEffect(()=>{
    if(user.birth !== "" && user.email !== ""  && user.name !== "" && user.password !== "" && confirmPassword !== "" && user.phoneNumber !== "" && user.username !== "" && passwordTF){
      setConfirmRegist(false);
    }else{
      setConfirmRegist(true);
    }
  },[passwordTF,confirmPassword,user])


    //이름 설정
    const handleSetName = (e) =>{
      setUser({...user,name:e.target.value});
    }
  
    //아이디 설정
    const handleSetUserName = (e) =>{
      setUser({...user,username:e.target.value});
    }
  
    //비밀번호 설정
    const handleSetPassword = (e) =>{
      setUser({...user,password:e.target.value});
      
    }

  //비밀번호 확인 설정
  const handleSetConfirmPassword = (e) =>{
    setConfirmPassword(e.target.value);
  }

  //이메일 설정
  const handleSetEmail = (e) =>{
    setUser({...user, email:e.target.value});
  }

  //전화번호
  const handleSetPhoneNumber = (e) =>{
    setUser({...user, phoneNumber:e.target.value});
  }

  //생년월일
  const handleSetBirth = (e) =>{
    setUser({...user, birth : e.target.value});
  }
  
  return (
    <>
      <div className="UserRegist-container">
        <div className='UserRegist-main-container'>

          <div className='UserRegist-info-container'>
            <div className='UserRegist-info-sub-container'>
              <label htmlFor='store-name'>이름</label>
              <input type='text' name='store-name' onChange={handleSetName} value={user.name} className='UserRegist-store-name-input' placeholder='식당 이름을 적어주세요.'/>
            </div>
          </div>

          <div className='UserRegist-info-container'>
            <div className='UserRegist-info-sub-container'>
              <label htmlFor='store-name'>아이디</label>
              <input type='text' name='store-name' onChange={handleSetUserName} value={user.username} className='UserRegist-store-name-input' placeholder='아이디를 적어주세요.'/>
            </div>
          </div>

          <div className='UserRegist-info-container'>
            <div className='UserRegist-info-sub-container'>
              <label htmlFor='store-name'>비밀번호</label>
              <input type='text' name='store-name' onChange={handleSetPassword} value={user.password} className='UserRegist-store-name-input' placeholder='비밀번호를 적어주세요.'/>
              
            </div>
          </div>

          <div className='UserRegist-info-container'>
            <div className='UserRegist-info-sub-container'>
              <label htmlFor='store-name'>비밀번호 확인</label>
              <div className='UserRegist-info-confirm-password-container'>
                <input type='text' style={{color:`${!passwordTF ? "red" : "black"}`}} name='store-name' onChange={handleSetConfirmPassword} value={confirmPassword} className='UserRegist-store-name-input' placeholder='비밀번호를 적어주세요.'/>
              </div>
            </div>
            
          </div>
          

          <div className='UserRegist-info-container'>
            <div className='UserRegist-info-sub-container'>
              <label htmlFor='store-name'>이메일</label>
              <input type='text' name='store-name' onChange={handleSetEmail} value={user.email} className='UserRegist-store-name-input' placeholder='이메일을 적어주세요.'/>
            </div>
          </div>

          <div className='UserRegist-info-container'>
            <div className='UserRegist-info-sub-container'>
              <label htmlFor='store-name'>전화번호</label>
              <input type='text' name='store-name' onChange={handleSetPhoneNumber} value={user.phoneNumber} className='UserRegist-store-name-input' placeholder='전화번호를 적어주세요.'/>
            </div>
          </div>

          <div className='UserRegist-info-container'>
            <div className='UserRegist-info-sub-container'>
              <label htmlFor='store-name'>생년월일</label>
              <input type='text' name='store-name' onChange={handleSetBirth} value={user.birth} className='UserRegist-store-name-input' placeholder='생년월일을 적어주세요.'/>
            </div>
          </div>

          <div className='UserRegist-btn-container'>
          <div className='UserRegist-serch-select-address-container'>
            <select onChange={handleSetRole}>
              {
                list.map((item)=>(
                  <option value={item}>
                    {item}
                  </option>
                ))
              }
            </select>
          </div>
            <input type='button' disabled={confirmRegist} onClick={regist} value={"등록"} className='regist-btn'/>
          </div>
        </div>

      </div>
    </>
  );
}

export default UserRegist;