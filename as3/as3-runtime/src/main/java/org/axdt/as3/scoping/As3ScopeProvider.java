/*
 * generated by Xtext
 */
package org.axdt.as3.scoping;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.axdt.as3.As3EFactory;
import org.axdt.as3.As3EPackage;
import org.axdt.as3.model.As3AccessExpression;
import org.axdt.as3.model.As3Class;
import org.axdt.as3.model.As3Executable;
import org.axdt.as3.model.As3FieldBinding;
import org.axdt.as3.model.As3Package;
import org.axdt.as3.model.As3Program;
import org.axdt.as3.model.As3PropertyIdentifier;
import org.axdt.as3.model.IIdentifier;
import org.axdt.avm.AvmEFactory;
import org.axdt.avm.access.IDefinitionProvider;
import org.axdt.avm.model.AvmDeclaredType;
import org.axdt.avm.model.AvmDeclaredTypeReference;
import org.axdt.avm.model.AvmField;
import org.axdt.avm.model.AvmGenericReference;
import org.axdt.avm.model.AvmIdentifiable;
import org.axdt.avm.model.AvmMember;
import org.axdt.avm.model.AvmType;
import org.axdt.avm.model.AvmTypeReference;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.linking.impl.LinkingHelper;
import org.eclipse.xtext.parsetree.CompositeNode;
import org.eclipse.xtext.parsetree.NodeUtil;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;
import org.eclipse.xtext.scoping.impl.AbstractScope;
import org.eclipse.xtext.util.SimpleAttributeResolver;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.inject.internal.Lists;

/**
 * This class contains custom scoping description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#scoping on
 * how and when to use it
 * 
 */
public class As3ScopeProvider extends AbstractDeclarativeScopeProvider {
	@Override
	protected IScope delegateGetScope(EObject context, EReference reference) {
		return super.delegateGetScope(context, reference);
	}

	IScope scope_AvmType(AvmTypeReference ctx, EReference ref) {
		return getDelegate().getScope(ctx, ref);
	}

	IScope scope_AvmIdentifiable(As3Program ctx, EReference ref) {
		return new As3ProgramScope(ctx, getDelegate().getScope(ctx, ref));
	}

	IScope scope_AvmIdentifiable(As3Package ctx, EReference ref) {
		// XXX is covered by workspace scope !?
		return null;
	}

	IScope scope_AvmIdentifiable(As3Class ctx, EReference ref) {
		return new AvmTypeScope(ctx, getScope(ctx.eContainer(), ref));
	}

	IScope scope_AvmIdentifiable(As3Executable ctx, EReference ref) {
		return new AvmExecutableScope(ctx, getScope(ctx.eContainer(), ref));
	}

	IScope scope_AvmIdentifiable(As3AccessExpression ctx, EReference ref) {
		return null;
	}

	IScope scope_AvmIdentifiable(As3PropertyIdentifier ctx, EReference ref) {
		As3EPackage pack = As3EPackage.eINSTANCE;
		if (ctx.eContainingFeature() == pack.getAs3PostfixOperator_Identifier()
				&& ctx.eContainer().eContainingFeature() == pack.getAs3AccessExpression_Operator())
			return new As3PropertyScope((As3AccessExpression) ctx.eContainer().eContainer());
		return null;
	}

	@Override
	public IScope getScope(EObject context, EReference reference) {
		return super.getScope(context, reference);
	}
}

abstract class AvmScope<T extends EObject> extends AbstractScope {
	protected static Function<AvmIdentifiable, IEObjectDescription> GetDesciption = new Function<AvmIdentifiable, IEObjectDescription>() {
		public IEObjectDescription apply(AvmIdentifiable from) {
			return EObjectDescription.create(from.getName(), from);
		}
	};
	protected final IScope parentScope;
	protected final T element;

	public AvmScope(T element, IScope parentScope) {
		this.element = element;
		this.parentScope = parentScope;
	}

	public IScope getOuterScope() {
		return parentScope;
	}

	@Override
	protected Iterable<IEObjectDescription> internalGetContents() {
		return Iterables.transform(getCandidates(), GetDesciption);
	}

	protected abstract Iterable<? extends AvmIdentifiable> getCandidates();
}

class As3ProgramScope extends AvmScope<As3Program> {

	public As3ProgramScope(As3Program element, IScope parentScope) {
		super(element, parentScope);
	}

	@Override
	protected Iterable<? extends AvmIdentifiable> getCandidates() {
		return Iterables.concat(element.getMembers(), element.getTypes());
	}
}

class AvmExecutableScope extends AvmScope<As3Executable> {

	public AvmExecutableScope(As3Executable element, IScope parentScope) {
		super(element, parentScope);
	}

	@Override
	protected Iterable<? extends AvmIdentifiable> getCandidates() {
		return Iterables.concat(element.getParameters(),
				element.getDeclarations());
	}
}

class AvmTypeScope extends AvmScope<AvmDeclaredType> {

	public AvmTypeScope(AvmDeclaredType element, IScope parentScope) {
		super(element, parentScope);
	}

	@Override
	protected Iterable<? extends AvmIdentifiable> getCandidates() {
		return Iterables.filter(getAllMembers(), new Predicate<AvmMember>() {
			private final Set<String> names = Sets.newHashSet();
			public boolean apply(AvmMember input) {
				String name = input.getName();
				return name != null && names.add(name);
			}
		});
	}

	protected Iterable<AvmMember> getAllMembers() {
		List<AvmMember> result = Lists.newArrayList();
		for (AvmDeclaredType type = element; type != null; type = getSuperType(type))
			result.addAll(type.getMembers());
		return result;
	}

	protected AvmDeclaredType getSuperType(AvmDeclaredType type) {
		// TODO how to handle interfaces ?
		AvmTypeReference ref = type.getExtendedClass();
		if (ref != null) {
			AvmType parent = ref.getType();
			if (parent.eIsProxy()) {
				InternalEObject internal = (InternalEObject) parent;
				URI proxyURI = internal.eProxyURI();
				// if it is an avm url, ecore cannot resolve it 
				if (IDefinitionProvider.PROTOCOL.equals(proxyURI.scheme())) {
					String qualifiedName = proxyURI.toString().replaceFirst(IDefinitionProvider.PROTOCOL+":/types/", "");
					// lets lookup the type name in the parent scope
					IEObjectDescription description = parentScope.getContentByName(qualifiedName);
					if (description != null) {
						EObject result = description.getEObjectOrProxy();
						// if resolution was successful
						if (result != null && !result.eIsProxy() && result instanceof AvmDeclaredType) {
							// set the resolve type so we can skip the scope lookup next time
							// XXX: think about class path visibility.
							//		the reference target might differ between projects
							AvmDeclaredType decl = (AvmDeclaredType) result;
							((AvmDeclaredTypeReference)ref).setType(decl);
							return decl;
						}
							
					}
				} else {
					parent = (AvmType) EcoreUtil2.resolve(parent, ref);
				}
			}
			if (!parent.eIsProxy() && parent instanceof AvmDeclaredType)
				return (AvmDeclaredType) parent;
		}
		return null;
	}
}

class As3PropertyScope extends AvmScope<As3AccessExpression> {


	public As3PropertyScope(As3AccessExpression element) {
		super(element, IScope.NULLSCOPE);
	}

	@Override
	protected Iterable<? extends AvmIdentifiable> getCandidates() {
		// TODO resolve expression type 
		AvmType type = AvmEFactory.eINSTANCE.createAvmGeneric();
		if (type != null) {
			IIdentifier identifier = element.getOperator().getIdentifier();
			if (type.isDynamic() && identifier instanceof As3PropertyIdentifier) {
				String text = getReferenceText((As3PropertyIdentifier) identifier);
				if (text != null)
					return Collections.singleton(getDynamicIdentifiable(text));
			}
		}
		return Iterables.emptyIterable();
	}
	public String getReferenceText(As3PropertyIdentifier ident) {
		CompositeNode node = NodeUtil.getNodeAdapter(ident).getParserNode();
		return new LinkingHelper().getCrossRefNodeAsString(node, false);
	}
	public AvmIdentifiable getDynamicIdentifiable(final String value) {
		Resource resource = getTempResource();
		EObject find = null;
		for (EObject obj: resource.getContents()) {
			if (obj instanceof As3FieldBinding) {
				if (value.equals(((As3FieldBinding) obj).getName()))
					find = obj;
			}
		}
		As3FieldBinding field;
		if (find != null) {
			field = (As3FieldBinding) find;
		} else {
			field = As3EFactory.eINSTANCE.createAs3FieldBinding();
			field.setName(value);
			getTempResource().getContents().add(field);
		}
		return field;
	}
	protected Resource getTempResource() {
		if (tempResource != null)
			return tempResource;
		URI tempUri = URI.createPlatformPluginURI("generic.avm", false);
		ResourceSet set = element.eResource().getResourceSet();
		tempResource = set.getResource(tempUri, false);
		if (tempResource == null)
			tempResource = set.createResource(tempUri);
		return tempResource;
	}
	private static Resource tempResource = null;
}

class AvmGenericScope<T extends EObject> extends AvmScope<T> {

	private static Resource tempResource;

	public AvmGenericScope(T element, EReference reference) {
		super(element, IScope.NULLSCOPE);
	}

	@Override
	protected Iterable<? extends AvmIdentifiable> getCandidates() {
		return null;
	}


	protected Resource getTempResource() {
		if (tempResource != null)
			return tempResource;
		URI tempUri = URI.createPlatformPluginURI("generic.avm", false);
		ResourceSet set = element.eResource().getResourceSet();
		tempResource = set.getResource(tempUri, false);
		if (tempResource == null)
			tempResource = set.createResource(tempUri);
		return tempResource;
	}
}

class PostfixScope extends AbstractScope {

	protected final EObject expression;
	protected final As3PropertyIdentifier context;
	private Resource tempResource;

	public PostfixScope(EObject eObject, As3PropertyIdentifier ctx) {
		this.expression = eObject;
		this.context = ctx;
	}

	public IScope getOuterScope() {
		return IScope.NULLSCOPE;
	}

	@Override
	protected Iterable<IEObjectDescription> internalGetContents() {
		List<IEObjectDescription> result = Lists.newArrayList();
		if (expression instanceof As3PropertyIdentifier) {
			As3PropertyIdentifier ident = (As3PropertyIdentifier) expression;
			CompositeNode node = NodeUtil.getNodeAdapter(context)
					.getParserNode();
			String nodeText = new LinkingHelper().getCrossRefNodeAsString(node,
					false);
			AvmIdentifiable reference = ident.getReference();
			if (reference == null || reference.eIsProxy()) {
				result.add(getGenericDescription(nodeText));
			} else {
				if (reference instanceof AvmField) {
					AvmField bind = (AvmField) reference;
					AvmTypeReference type = bind.getType();
					if (type == null || type instanceof AvmGenericReference)
						result.add(getGenericDescription(nodeText));
					else if (type.getType() instanceof As3PropertyIdentifier) {
						As3PropertyIdentifier typeIdent = (As3PropertyIdentifier) type
								.getType();
						if ("*".equals(typeIdent.getName()))
							result.add(getGenericDescription(nodeText));
						else {
							AvmIdentifiable typeTarget = typeIdent
									.getReference();
							if (typeTarget instanceof AvmDeclaredType) {
								AvmDeclaredType classDef = (AvmDeclaredType) typeTarget;
								for (EObject child : classDef.getMembers()) {
									String name = SimpleAttributeResolver.NAME_RESOLVER
											.apply(child);
									result.add(EObjectDescription.create(name,
											child));
								}
							}
						}
					}
				}
			}
		}
		return result;
	}

	protected IEObjectDescription getGenericDescription(String name) {
		As3PropertyIdentifier identifiable = As3EFactory.eINSTANCE
				.createAs3PropertyIdentifier();
		identifiable.setName(name);
		getTempResource().getContents().add(identifiable);
		return EObjectDescription.create(identifiable.getName(), identifiable);
	}

	protected Resource getTempResource() {
		if (tempResource != null)
			return tempResource;
		URI tempUri = context.eResource().getURI()
				.appendFileExtension("generic");
		ResourceSet set = context.eResource().getResourceSet();
		tempResource = set.getResource(tempUri, false);
		if (tempResource == null)
			tempResource = set.createResource(tempUri);
		else
			tempResource.getContents().clear();
		return tempResource;
	}
}