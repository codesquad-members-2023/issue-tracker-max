import {useState} from "react";
import {styled, useTheme} from "styled-components";
import {Button} from "../../components/Button";
import {InformationTag} from "../../components/InformationTag";
import {LabelData} from "./Label";
import {LabelEditor} from "./LabelEditor";

export function LabelTableElement({label}: { label: LabelData }) {
  const [isEditing, setIsEditing] = useState(false);

  const onClickEdit = () => {
    setIsEditing(true);
  };

  const closeEditor = () => {
    setIsEditing(false);
  };

  const theme = useTheme();

  return isEditing ? (
      <LabelEditor type="edit" label={label} onClickClose={closeEditor}/>
  ) : (
      <Div>
        <LabelTag>
          <InformationTag
              size="S"
              value={label.name}
              fill={label.background}
              fontColor={label.color}
          />
        </LabelTag>
        <Description>{label.description}</Description>
        <Buttons>
          <Button size="S" buttonType="Ghost" icon="edit" onClick={onClickEdit}>
            편집
          </Button>
          <Button
              size="S"
              buttonType="Ghost"
              icon="trash"
              color={theme.color.dangerSurfaceDefault}
          >
            삭제
          </Button>
        </Buttons>
      </Div>
  );
}

const Div = styled.div`
  width: 100%;
  min-height: 96px;
  padding: 32px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 32px;
  box-sizing: border-box;
  border-top: solid 1px ${({theme}) => theme.color.neutralBorderDefault};
`;

const Buttons = styled.div`
  display: flex;
  width: 130px;
  align-items: flex-start;
  gap: 24px;
`;

const Description = styled.span`
  width: 870px;
  display: flex;
  font: ${({theme}) => theme.font.displayMedium16};
  color: ${({theme}) => theme.color.neutralTextWeak};
  flex: 1;
`;

const LabelTag = styled.div`
  width: 96px;
`;
