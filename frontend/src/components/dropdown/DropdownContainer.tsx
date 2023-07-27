import { useState } from "react";
import { styled } from "styled-components";
import DropdownIndicator from "./DropdownIndicator";
import DropdownPanel from "./DropdownPanel";

export default function DropdownContainer() {
  const [isPanelOpened, setIsPanelOpened] = useState(false);

  return (
    <StyledContainer>
      <DropdownIndicator onClick={() => setIsPanelOpened(true)}>
        버튼
      </DropdownIndicator>
      {isPanelOpened && (
        <>
          <div
            className="dropdown__dim"
            onClick={() => setIsPanelOpened(false)}
          ></div>
          <DropdownPanel alignment="left" />
        </>
      )}
    </StyledContainer>
  );
}

const StyledContainer = styled.div`
  position: relative;

  & .dropdown__dim {
    position: fixed;
    content: "";
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 50;
  }
`;
