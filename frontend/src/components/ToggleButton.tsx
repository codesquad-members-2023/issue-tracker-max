import { useState } from "react";
import { styled } from "styled-components";
import { Icon } from "./icon/Icon";

export const Toggle = ({ onClick }: { onClick: () => void }) => {
  const [isOn, setIsOn] = useState(false);

  const toggleHandler = () => {
    setIsOn(!isOn);
    onClick();
  };

  return (
    <>
      <ToggleContainer onClick={toggleHandler}>
        <ToggleSwitch className={`toggle-container`}>
          <ToggleCircle $isOn={isOn} className={`toggle-circle`}>
            <Icon name={`${isOn ? "moon" : "sun"}`} color="#000" />
          </ToggleCircle>
        </ToggleSwitch>
      </ToggleContainer>
    </>
  );
};

const ToggleContainer = styled.div`
  width: 50px;
  height: 25px;
`;

const ToggleSwitch = styled.div`
  width: 55px;
  height: 27px;
  display: flex;
  align-items: center;
  border-radius: 30px;
  background-color: #d5d5d5;
  padding: 3px;
  box-sizing: border-box;
`;

const ToggleCircle = styled.div<{ $isOn: boolean }>`
  width: 22px;
  height: 22px;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgb(255, 254, 255);
  position: absolute;
  transform: ${({ $isOn }) => ($isOn ? "translateX(27px)" : "translateX(0px)")};
  transition: transform 0.4s;
`;
