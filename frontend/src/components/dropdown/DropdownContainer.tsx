import { Children, ReactNode, useState } from "react";
import { styled } from "styled-components";

export function DropdownContainer({ children }: { children: ReactNode }) {
  const [isPanelOpened, setIsPanelOpened] = useState(false);

  const openPanel = () => {
    setIsPanelOpened(true);
  };

  const closePanel = () => {
    setIsPanelOpened(false);
  };

  return (
    <StyledContainer>
      {isPanelOpened && <div className="dropdown__dim" onClick={closePanel} />}
      {Children.map(children, (child, index) => {
        if (index === 0) {
          return (
            <div key={index} onClick={openPanel}>
              {child}
            </div>
          );
        }
        if (isPanelOpened) {
          return child;
        }
        return null;
      })}
    </StyledContainer>
  );
}

const StyledContainer = styled.div`
  width: fit-content;
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
